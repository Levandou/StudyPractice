package com.velagissellint.domain.useCases.document

class DocumentUseCase(private val documentRepository: DocumentRepository) {

    fun getDocumentList(profileId: String) = documentRepository.getDocumentList(profileId)

    fun getDocument(documentId: Int) = documentRepository.getDocument(documentId)
}