package com.velagissellint.presentation.diplom.document_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velagissellint.domain.models.diplom.Document
import com.velagissellint.domain.models.diplom.Task
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.domain.useCases.document.DocumentUseCase
import com.velagissellint.domain.useCases.user.ProfileUseCase
import com.velagissellint.domain.useCases.user.UserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class DocumentationsViewModel(
    private val userUseCase: UserUseCase,
    private val profileUseCase: ProfileUseCase,
    private val documentUseCase: DocumentUseCase
) : ViewModel() {
    private val profileId: String by lazy {
        userUseCase.getUser()?.id ?: throw Throwable("ProfileId is Null")
    }
    val documentListFlow = MutableStateFlow<List<Document>?>(null)

    fun getDocumentList() {
        viewModelScope.launch(Dispatchers.IO) {
            val result: MutableStateFlow<RequestResult<List<Document>>> = documentUseCase.getDocumentList(profileId)
            result.collectLatest { listTaskResult ->
                if (listTaskResult !is RequestResult.Success) return@collectLatest

                documentListFlow.value = listTaskResult.data
            }
        }
    }
}