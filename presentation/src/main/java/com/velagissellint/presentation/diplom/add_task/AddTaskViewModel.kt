package com.velagissellint.presentation.diplom.add_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velagissellint.domain.models.diplom.Profile
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.domain.useCases.task.TaskUseCase
import com.velagissellint.domain.useCases.user.ProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AddTaskViewModel(
    private val profileUseCase: ProfileUseCase,
    private val taskUseCase: TaskUseCase
) : ViewModel() {

    val profileListFlow = MutableStateFlow<List<Profile>>(emptyList())

    fun getAllProfilesFromTeam() {
        val result: MutableStateFlow<RequestResult<List<Profile>>> =
            profileUseCase.getAllProfiles()

        viewModelScope.launch(Dispatchers.IO) {
            result.collectLatest {
                if (it is RequestResult.Success)
                    profileListFlow.value = it.data
            }
        }
    }
}