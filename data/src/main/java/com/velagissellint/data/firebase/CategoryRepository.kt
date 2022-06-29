package com.velagissellint.data.firebase

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject


class CategoryRepository@Inject constructor(
    private val context: Context
) {
    fun addCategory(nameCategory:String){
        val fStore = FirebaseFirestore.getInstance()
        fStore.collection("Category")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var count = 0
                    for (document in task.result)
                        count++

                    val df = fStore.collection("Category").document((count+1).toString())
                    val userInfo: MutableMap<String, Any> = mutableMapOf()
                    userInfo["nameOfCategory"] = nameCategory
                    df.set(userInfo)
                } else {
                    Log.d("Errorgettingdocuments ", task.exception.toString())
                }
            }
//        val df = fStore.collection("Category").document()
//        val userInfo: MutableMap<String, Any> = mutableMapOf()
//        userInfo["nameOfCategory"] = nameCategory
//        df.set(userInfo)
        //Toast.makeText(context, "Регистрация прошла успешна", Toast.LENGTH_LONG).show()
    }

}