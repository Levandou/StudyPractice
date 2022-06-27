package com.velagissellint.domain.useCases.registration

import com.velagissellint.domain.models.User

interface CreateAccountRepository {
    fun createAccount(email: String, password: String, user: User)
}