package com.velagissellint.domain.useCases.document

import com.velagissellint.domain.models.diplom.Document
import com.velagissellint.domain.network.RequestResult
import kotlinx.coroutines.flow.MutableStateFlow

interface DocumentRepository {
    fun getDocumentList(profileId: String): MutableStateFlow<RequestResult<List<Document>>>
    fun getDocument(documentId: Int): MutableStateFlow<RequestResult<Document>>
}