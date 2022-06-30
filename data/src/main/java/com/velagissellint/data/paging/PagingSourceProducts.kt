package com.velagissellint.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.velagissellint.domain.models.FoldingRooms
import com.velagissellint.domain.models.Product
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max

class PagingSourceProducts(private val id: String) : PagingSource<QuerySnapshot, Product>() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var listProduct: MutableList<Product>
    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Product> {
        return try {
            // Step 1
            val currentPage = params.key ?: db.collection("Product")
                .limit(20)
                .get()
                .await()
            // Step 2
            val lastDocumentSnapshot = currentPage.documents[currentPage.size() - 1]

            // Step 3
            val nextPage = db.collection("Product").limit(20).startAfter(lastDocumentSnapshot)
                .get()
                .await()

            var a: List<DocumentSnapshot> = currentPage.documents
            for (l in a)
                l.id
            if (id == "nothing") {
                listProduct = currentPage.toObjects(Product::class.java)







                for (i in 0 until listProduct.size) {
                    for (j in 0 until listProduct[i].soldInDay.size) {
                        if (dateStringPlusInt(
                                listProduct[i].dateOfManufacture[j],
                                listProduct[i].expirationDate
                            ) <=
                            Calendar.getInstance()[Calendar.DAY_OF_YEAR]
                        ) {
                            listProduct[i].dateOfManufacture.removeAt(j)
                            listProduct[i].inStock.removeAt(j)
                            listProduct[i].soldInDay.removeAt(j)


                        }
                    }
                    productReset(listProduct, i, a)
                }
                val listCountInStock: MutableList<Double> = mutableListOf()
                val listCountSoldInDay: MutableList<Double> = mutableListOf()
                for (m in 0..2) {

                    for (i in 0 until listProduct.size) {
                        for (j in 0 until listProduct[i].soldInDay.size)
                            if (listProduct[i].keeping == m)
                                listProduct[i].inStock[j] -= listProduct[i].soldInDay[j]
                        productReset(listProduct, i, a)
                    }

                    for (i in 0 until listProduct.size) {
                        var countInStock = 0.0
                        var countSoldInDay = 0.0
                        for (j in 0 until listProduct[i].soldInDay.size) {
                            if (listProduct[i].keeping == m) {
                                countInStock += listProduct[i].inStock[j] * listProduct[i].volume
                                countSoldInDay += listProduct[i].soldInDay[j]* listProduct[i].volume
                            }
                        }
                        listCountInStock.add(countInStock)
                        listCountSoldInDay.add(countSoldInDay)
                        productReset(listProduct, i, a)
                    }
                    var countSold = 0.0
                    for (i in listCountSoldInDay)
                        countSold += i
                    var countInStock = 0.0
                    for (i in listCountInStock)
                        countInStock += i


                    val c = db.collection("FoldingRooms").document(m.toString())
                        .get().await().toObject(FoldingRooms::class.java) as FoldingRooms
                    if (c.volume >= countSold+countInStock) {
                        for (i in 0 until listProduct.size) {
                            for (j in 0 until listProduct[i].soldInDay.size) {
                                if (listProduct[i].keeping == m) {
                                    listProduct[i].inStock.add(listProduct[i].nextOrder)
                                    listProduct[i].dateOfManufacture.add(Calendar.getInstance()[Calendar.DAY_OF_MONTH].toString()+
                                            (Calendar.getInstance()[Calendar.MONTH]+1).toString()+
                                            Calendar.getInstance()[Calendar.YEAR])

                                    listProduct[i].nextOrder = (listCountSoldInDay[i]/listProduct[i].volume).toInt()
                                }
                            }
                            productReset(listProduct, i, a)
                        }
                    } else {
                        var indexItem=0
                        var indexMaxSold=0
                        var maxSold=0
                        while (c.volume <= countSold+countInStock){
                            for (i in 0 until listProduct.size) {
                                for (j in 0 until listProduct[i].soldInDay.size) {
                                    if(listProduct[i].soldInDay[j]>maxSold){
                                        maxSold=listProduct[i].soldInDay[j]
                                        indexMaxSold=j
                                        indexItem=i
                                    }
                                }
                            }
                            listProduct[indexItem].soldInDay[indexMaxSold]-=1
                            countSold-=1
                        }
                        for (i in 0 until listProduct.size) {
                            for (j in 0 until listProduct[i].soldInDay.size) {
                                if (listProduct[i].keeping == m) {
                                    listProduct[i].inStock.add(listProduct[i].nextOrder)
                                    listProduct[i].dateOfManufacture.add(Calendar.getInstance()[Calendar.DAY_OF_MONTH].toString()+
                                            (Calendar.getInstance()[Calendar.MONTH]+1).toString()+
                                            Calendar.getInstance()[Calendar.YEAR])

                                    listProduct[i].nextOrder = (listCountSoldInDay[i]/listProduct[i].volume).toInt()
                                }
                            }
                            productReset(listProduct, i, a)
                        }
                    }
                }


            } else
                listProduct =
                    currentPage.toObjects(Product::class.java)
                        .filter { it.idCategory == id } as MutableList<Product>
            // Step 4
            LoadResult.Page(
                data = listProduct,
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            Log.d("das", e.toString())
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Product>): QuerySnapshot? {
        TODO("Not yet implemented")
    }

    fun dateStringPlusInt(dateString: String, day: Int): Int {
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        val date = formatter.parse(dateString)
        val cal: Calendar = Calendar.getInstance()
        cal.time = date
        Log.d("sda", (cal[Calendar.DAY_OF_YEAR] + day).toString())
        Log.d("sda2", Calendar.getInstance()[Calendar.DAY_OF_YEAR].toString())
        return cal[Calendar.DAY_OF_YEAR] + day
    }

    fun productReset(listProduct: MutableList<Product>, i: Int, a: List<DocumentSnapshot>) {
        db.collection("Product").document(a[i].id).delete()
        val df = db.collection("Product").document(a[i].id)
        val productInfo: MutableMap<String, Any> = mutableMapOf()
        productInfo["dateOfManufacture"] = listProduct[i].dateOfManufacture
        productInfo["volume"] = listProduct[i].volume
        productInfo["soldInDay"] = listProduct[i].soldInDay
        productInfo["price"] = listProduct[i].price
        productInfo["nextOrder"] = listProduct[i].nextOrder
        productInfo["keeping"] = listProduct[i].keeping
        productInfo["idCategory"] = listProduct[i].idCategory
        productInfo["expirationDate"] = listProduct[i].expirationDate
        productInfo["inStock"] = listProduct[i].inStock
        productInfo["name"] = listProduct[i].name
        df.set(productInfo)
    }

}