package com.velagissellint.domain.useCases.logIn

import com.velagissellint.domain.models.User
import com.velagissellint.domain.network.RequestResult
import kotlinx.coroutines.flow.MutableStateFlow

class LogInUseCase(private val logInRepository: LogInRepository) {
    fun logIn(email: String, password: String): MutableStateFlow<RequestResult<Boolean>> = logInRepository.logIn(email, password)

    fun saveUser(user: User) = logInRepository.saveUser(user)

    fun loadIsAdmin(email: String) = logInRepository.loadIsAdmin(email)
    fun update(user: User) = logInRepository.update(user)
}