package com.velagissellint.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.velagissellint.domain.models.diplom.Document
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.domain.useCases.document.DocumentRepository
import kotlinx.coroutines.flow.MutableStateFlow

class DocumentRepositoryFb : DocumentRepository {
    override fun getDocumentList(profileId: String): MutableStateFlow<RequestResult<List<Document>>> =
        MutableStateFlow<RequestResult<List<Document>>>(RequestResult.Loading).also { result ->
            val fStore = FirebaseFirestore.getInstance()
            val collectionRef = fStore.collection("Document")

            collectionRef.whereEqualTo("profileId", profileId).get()
                .addOnSuccessListener { documents ->
                    result.value = RequestResult.Success(documents.map {
                        Document(
                            id = it?.getLong("id")?.toInt(),
                            profileId = profileId,
                            signed = it?.getBoolean("signed"),
                            title = it?.getString("title"),
                            fullName = it?.getString("fullName"),
                            date = it?.getString("date")
                        )
                    })
                }.addOnFailureListener {
                    result.value = RequestResult.Error("addOnFailureListener")
                }
        }

    override fun getDocument(documentId: Int): MutableStateFlow<RequestResult<Document>> =
        MutableStateFlow<RequestResult<Document>>(RequestResult.Loading).also { result ->
            val fStore = FirebaseFirestore.getInstance()
            val collectionRef = fStore.collection("Document")

            collectionRef.whereEqualTo("id", documentId).get()
                .addOnSuccessListener { documents ->
                    val selectedDocument = documents.documents[0]
                    result.value = RequestResult.Success(
                        Document(
                            id = selectedDocument?.getLong("id")?.toInt(),
                            profileId = selectedDocument?.getString("profileId"),
                            signed = selectedDocument?.getBoolean("signed"),
                            title = selectedDocument?.getString("title"),
                            fullName = selectedDocument?.getString("fullName"),
                            date = selectedDocument?.getString("date")
                        )
                    )
                }.addOnFailureListener {
                    result.value = RequestResult.Error("addOnFailureListener")
                }
        }
}