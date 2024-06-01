package com.velagissellint.presentation.diplom.admin.check_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velagissellint.domain.models.diplom.Chart
import com.velagissellint.domain.models.diplom.Profile
import com.velagissellint.domain.models.diplom.Statement
import com.velagissellint.domain.models.diplom.Team
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.domain.useCases.chart.ChartUseCase
import com.velagissellint.domain.useCases.statement.StatementUseCase
import com.velagissellint.domain.useCases.team.TeamUseCase
import com.velagissellint.domain.useCases.user.ProfileUseCase
import com.velagissellint.domain.useCases.user.UserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AdminProfileViewModel(
    private val profileUseCase: ProfileUseCase,
    private val teamUseCase: TeamUseCase,
    private val chartUseCase: ChartUseCase
) : ViewModel() {
    val profileFlow = MutableStateFlow<RequestResult<Profile>>(RequestResult.Loading)
    val teamListFlow = MutableStateFlow<RequestResult<List<Team>>>(RequestResult.Loading)
    val teamFlow = MutableStateFlow<RequestResult<Team>>(RequestResult.Loading)
    val chartFlow = MutableStateFlow<RequestResult<Chart>>(RequestResult.Loading)

    fun getProfile(profileId: String) {
        val result: MutableStateFlow<RequestResult<Profile>> =
            profileUseCase.getProfile(profileId)

        viewModelScope.launch(Dispatchers.IO) {
            result.collectLatest {
                profileFlow.value = it
            }
        }
    }

    fun getAllTeams(){
        val result: MutableStateFlow<RequestResult<List<Team>>> = teamUseCase.getAllTeams()

        viewModelScope.launch(Dispatchers.IO) {
            result.collectLatest {
                teamListFlow.value = it
            }
        }
    }

    fun getTeam(teamId: Int){
        val result: MutableStateFlow<RequestResult<Team>> = teamUseCase.getTeam(teamId)

        viewModelScope.launch(Dispatchers.IO) {
            result.collectLatest {
                teamFlow.value = it
            }
        }
    }

    fun showNextWeek(profileId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result: MutableStateFlow<RequestResult<Chart>> =
                chartUseCase.getCurrentChart(profileId)
            result.collectLatest {
                chartFlow.value = it
            }
        }
    }

    fun showLastWeek(profileId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result: MutableStateFlow<RequestResult<Chart>> =
                chartUseCase.getNextChart(profileId)
            result.collectLatest {
                chartFlow.value = it
            }
        }
    }
}