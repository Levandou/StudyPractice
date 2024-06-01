package com.velagissellint.domain.useCases.team

import com.velagissellint.domain.models.diplom.Team
import com.velagissellint.domain.network.RequestResult
import kotlinx.coroutines.flow.MutableStateFlow

interface TeamRepository {
    fun getTeam(teamId: Int): MutableStateFlow<RequestResult<Team>>
    fun addTeam(teamName: String)
    fun getAllTeams(): MutableStateFlow<RequestResult<List<Team>>>
}