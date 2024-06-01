package com.velagissellint.presentation.diplom.document

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velagissellint.domain.models.diplom.Document
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.domain.useCases.document.DocumentUseCase
import com.velagissellint.domain.useCases.user.UserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DocumentViewModel(
    private val userUseCase: UserUseCase,
    private val documentUseCase: DocumentUseCase
) : ViewModel() {
    private val profileId: String by lazy {
        userUseCase.getUser()?.id ?: throw Throwable("ProfileId is Null")
    }
    val documentFlow = MutableStateFlow<Document?>(null)

    fun getDocument(documentId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result: MutableStateFlow<RequestResult<Document>> = documentUseCase.getDocument(documentId)
            result.collectLatest { listTaskResult ->
                if (listTaskResult !is RequestResult.Success) return@collectLatest

                documentFlow.value = listTaskResult.data
            }
        }
    }
}