package com.velagissellint.domain.useCases.user

import com.velagissellint.domain.models.User

interface UserRepository {
    fun getCurrentUser() : User?
}