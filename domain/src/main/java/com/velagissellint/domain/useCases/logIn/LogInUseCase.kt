package com.velagissellint.domain.useCases.logIn

import javax.inject.Inject

class LogInUseCase @Inject constructor(private val logInRepository: LogInRepository) {
    fun logIn(email: String, password: String) {
        logInRepository.logIn(email, password)
    }
}