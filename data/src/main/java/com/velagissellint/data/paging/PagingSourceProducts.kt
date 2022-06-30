package com.velagissellint.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.velagissellint.domain.models.Category
import com.velagissellint.domain.models.Product
import kotlinx.coroutines.tasks.await

class PagingSourceProducts(private val id:String) : PagingSource<QuerySnapshot, Product>() {
    private val db = FirebaseFirestore.getInstance()
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

            // Step 4
            LoadResult.Page(
                data = currentPage.toObjects(Product::class.java).filter { it.idCategory== id},
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            Log.d("das",e.toString())
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Product>): QuerySnapshot? {
        TODO("Not yet implemented")
    }
}