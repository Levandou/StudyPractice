package com.velagissellint.data.firebase

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


class CategoryRepository @Inject constructor(
    private val context: Context
) {
    fun addCategory(nameCategory: String) {
        val fStore = FirebaseFirestore.getInstance()
        val df = fStore.collection("Category").document()
        val userInfo: MutableMap<String, Any> = mutableMapOf()
        userInfo["nameOfCategory"] = nameCategory
        userInfo["id"] = df.id
        df.set(userInfo)
    }
}