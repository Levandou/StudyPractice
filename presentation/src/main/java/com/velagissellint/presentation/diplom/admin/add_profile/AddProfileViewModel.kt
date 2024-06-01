package com.velagissellint.presentation.diplom.admin.add_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velagissellint.domain.models.diplom.Team
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.domain.useCases.team.TeamUseCase
import com.velagissellint.domain.useCases.user.ProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AddProfileViewModel(
    private val profileUseCase: ProfileUseCase,
    private val teamUseCase: TeamUseCase
) : ViewModel() {
    val teamListFlow = MutableStateFlow<RequestResult<List<Team>>>(RequestResult.Loading)

    fun getAllTeams(){
        val result: MutableStateFlow<RequestResult<List<Team>>> = teamUseCase.getAllTeams()

        viewModelScope.launch(Dispatchers.IO) {
            result.collectLatest {
                teamListFlow.value = it
            }
        }
    }

}