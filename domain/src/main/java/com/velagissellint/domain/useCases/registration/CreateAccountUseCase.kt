package com.velagissellint.domain.useCases.registration

import com.velagissellint.domain.models.User
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(private val createAccountRepository: CreateAccountRepository) {
    fun createAccount(email: String, password: String, user: User) {
        createAccountRepository.createAccount(email, password, user)
    }
}