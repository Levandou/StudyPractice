package com.velagissellint.presentation.diplom.admin.search_admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velagissellint.domain.models.diplom.Profile
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.domain.useCases.user.ProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchProfileViewModel(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    val profileFlow = MutableStateFlow<RequestResult<Profile>>(RequestResult.Loading)
    private var searchJob: Job? = null

    fun getProfile(name: String) {

        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            val result: MutableStateFlow<RequestResult<Profile>> =
                profileUseCase.getProfileFromName(name)
            result.collectLatest {
                profileFlow.value = it
            }
        }
    }
}