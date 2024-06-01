package com.velagissellint.domain.useCases.logIn

import com.velagissellint.domain.models.User
import com.velagissellint.domain.network.RequestResult
import kotlinx.coroutines.flow.MutableStateFlow

interface LogInRepository {
    fun logIn(email: String, password: String): MutableStateFlow<RequestResult<Boolean>>

    fun saveUser(user: User)

    fun loadIsAdmin(email: String): MutableStateFlow<RequestResult<Boolean>>
    fun update(user: User)
}