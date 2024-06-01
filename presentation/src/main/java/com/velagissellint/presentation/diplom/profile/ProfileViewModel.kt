package com.velagissellint.presentation.diplom.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velagissellint.domain.models.diplom.Profile
import com.velagissellint.domain.models.diplom.Statement
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.domain.useCases.chart.ChartUseCase
import com.velagissellint.domain.useCases.statement.StatementUseCase
import com.velagissellint.domain.useCases.user.ProfileUseCase
import com.velagissellint.domain.useCases.user.UserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userUseCase: UserUseCase,
    private val profileUseCase: ProfileUseCase,
    private val statementUseCase: StatementUseCase,
    private val chartUseCase: ChartUseCase
) : ViewModel() {
    private val profileId: String by lazy {
        userUseCase.getUser()?.id ?: throw Throwable("ProfileId is Null")
    }
    val statementFlow = MutableStateFlow<RequestResult<Statement>>(RequestResult.Loading)
    val profileFlow = MutableStateFlow<RequestResult<Profile>>(RequestResult.Loading)

    fun getStatement() {
        val result = statementUseCase.getStatement(profileId)
        viewModelScope.launch(Dispatchers.IO) {
            result.collectLatest {
                statementFlow.value = it
            }
        }
    }

    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            val result: MutableStateFlow<RequestResult<Profile>> =
                profileUseCase.getProfile(profileId)
            result.collectLatest {
                profileFlow.value = it
            }
        }
    }

    fun saveStatement(newPosition: String, text: String) {
        statementUseCase.saveStatement(profileId, newPosition, text)

        getStatement()
    }

    fun saveChart(
        argumentMo: Int,
        argumentTu: Int,
        argumentWe: Int,
        argumentTh: Int,
        argumentFr: Int
    ) {
        chartUseCase.saveChart(
            profileId,
            argumentMo,
            argumentTu,
            argumentWe,
            argumentTh,
            argumentFr
        )
    }
}