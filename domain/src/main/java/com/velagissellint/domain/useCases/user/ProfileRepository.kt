package com.velagissellint.domain.useCases.user

import com.velagissellint.domain.models.diplom.Profile
import com.velagissellint.domain.network.RequestResult
import kotlinx.coroutines.flow.MutableStateFlow

interface ProfileRepository {
    fun getProfile(profileId: String): MutableStateFlow<RequestResult<Profile>>

    fun getProfileName(profileId: String?): MutableStateFlow<RequestResult<Pair<String, String>>>

    fun getProfileFromName(name: String): MutableStateFlow<RequestResult<Profile>>

    fun getAllProfiles(): MutableStateFlow<RequestResult<List<Profile>>>
}