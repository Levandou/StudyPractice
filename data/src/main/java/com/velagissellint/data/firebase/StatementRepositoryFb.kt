package com.velagissellint.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.velagissellint.domain.models.diplom.Statement
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.domain.useCases.statement.StatementRepository
import kotlinx.coroutines.flow.MutableStateFlow

class StatementRepositoryFb : StatementRepository {
    override fun createStatement(
        profileId: String, text: String, newPosition: String
    ) {
        val fStore = FirebaseFirestore.getInstance()
        val collectionRef = fStore.collection("Statement")

        val query = collectionRef.orderBy("id", Query.Direction.DESCENDING).limit(1)
        var idLast = 0
        query.get().addOnSuccessListener { querySnapshot ->
            if (!querySnapshot.isEmpty) {
                val lastAddedItem = querySnapshot.documents.getOrNull(0)
                idLast = lastAddedItem?.get("id").toString().toInt()
            }
        }

        val newTask = hashMapOf(
            "id" to idLast,
            "profileId" to profileId,
            "text" to text,
            "newPosition" to newPosition,
        )
        fStore.collection("Statement").document().set(newTask)
    }

    override fun getStatement(profileId: String): MutableStateFlow<RequestResult<Statement>> =
        MutableStateFlow<RequestResult<Statement>>(RequestResult.Loading).also {
            val fStore = FirebaseFirestore.getInstance()
            val collectionRef = fStore.collection("Statement")

            collectionRef.whereEqualTo("profileId", profileId).get()
                .addOnSuccessListener { documents ->
                    val selectedDocument = documents.documents.getOrNull(0)
                    it.value = RequestResult.Success(
                        Statement(
                            id = selectedDocument?.getLong("id")?.toInt(),
                            profileId = profileId,
                            text = selectedDocument?.getString("text"),
                            newPosition = selectedDocument?.getString("newPosition")
                        )
                    )
                }.addOnFailureListener { exception ->
                    it.value = RequestResult.Error("addOnFailureListener")
                }
        }

    /*override fun getStatement(profileId: String, callbackResult: CallbackResult<Statement>): MutableStateFlow<RequestResult<Statement>> {
        val fStore = FirebaseFirestore.getInstance()
        val collectionRef = fStore.collection("Statement")
        val result = MutableStateFlow<RequestResult<Statement>>(RequestResult.Loading)

        collectionRef.whereEqualTo("profileId", profileId).get().addOnSuccessListener { documents ->
                val selectedDocument = documents.documents.getOrNull(0)
                result.value = RequestResult.Success(
                    Statement(
                        id = selectedDocument?.getLong("id")?.toInt(),
                        profileId = profileId,
                        text = selectedDocument?.getString("text"),
                        newPosition = selectedDocument?.getString("newPosition")
                    )
                )
            }.addOnFailureListener {
                result.update {
                    RequestResult.Error("addOnFailureListener")
                }
            }
        return result
    }*/
}
