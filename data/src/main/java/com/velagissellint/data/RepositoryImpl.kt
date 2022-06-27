package com.velagissellint.data

import com.velagissellint.data.firebase.UserRepository
import com.velagissellint.domain.models.User
import com.velagissellint.domain.useCases.logIn.LogInRepository
import com.velagissellint.domain.useCases.registration.CreateAccountRepository

class RepositoryImpl(
    private val userRepository: UserRepository
) : CreateAccountRepository, LogInRepository {
    override fun createAccount(
        email: String,
        password: String,
        user: User
    ) {
        userRepository.createAccount(email, password, user)
    }

    override fun logIn(email: String, password: String) {
        userRepository.logIn(email, password)
    }
}