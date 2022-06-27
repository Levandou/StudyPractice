package com.velagissellint.domain.useCases.logIn

interface LogInRepository {
    fun logIn(email: String, password: String)
}