package com.velagissellint.domain.useCases.user

import com.velagissellint.domain.models.diplom.Profile
import com.velagissellint.domain.models.diplom.Team
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.domain.useCases.chart.ChartRepository
import com.velagissellint.domain.useCases.team.TeamRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileUseCase(
    private val profileRepository: ProfileRepository,
    private val teamRepository: TeamRepository,
    private val chartRepository: ChartRepository
) {
    fun getProfile(profileId: String) = profileRepository.getProfile(profileId)

    suspend fun getProfileName(profileId: String?) = profileRepository.getProfileName(profileId)

    fun getProfileFromName(name: String) = profileRepository.getProfileFromName(name)
    fun getAllProfiles() = profileRepository.getAllProfiles()
}