package com.velagissellint.data

import com.velagissellint.data.db.UserDao
import com.velagissellint.domain.models.User
import com.velagissellint.domain.useCases.user.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
): UserRepository {
    override fun getCurrentUser(): User? = userDao.getCurrentUser()
}