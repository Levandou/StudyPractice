package com.velagissellint.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class CommentRepository {
    fun createTask(
        taskId: Int,
        authorId: String,
        comment: String
    ) {
        val fStore = FirebaseFirestore.getInstance()
        val collectionRef = fStore.collection("Comment")

        val query = collectionRef.orderBy("id", Query.Direction.DESCENDING).limit(1)
        var idLast = 0
        query.get().addOnSuccessListener { querySnapshot ->
            if (!querySnapshot.isEmpty) {
                val lastAddedItem = querySnapshot.documents[0]
                idLast = lastAddedItem.get("id").toString().toInt()
            }
        }

        val newTask = hashMapOf(
            "id" to idLast,
            "taskId" to taskId,
            "authorId" to authorId,
            "comment" to comment,
        )
        fStore.collection("Comment").document().set(newTask)
    }
}