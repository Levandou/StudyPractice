package com.velagissellint.domain.useCases.team

class TeamUseCase(private val teamRepository: TeamRepository) {
    fun addTeam(teamName: String) = teamRepository.addTeam(teamName)

    fun getTeam(teamId: Int) = teamRepository.getTeam(teamId)
    fun getAllTeams() = teamRepository.getAllTeams()
}