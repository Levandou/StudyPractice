package com.velagissellint.presentation.diplom.admin.add_team

import androidx.lifecycle.ViewModel
import com.velagissellint.domain.useCases.team.TeamUseCase

class AddTeamViewModel(
    private val teamUseCase: TeamUseCase
) : ViewModel() {
    fun addTeam(teamName: String) = teamUseCase.addTeam(teamName)
}