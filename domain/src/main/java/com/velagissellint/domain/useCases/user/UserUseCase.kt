package com.velagissellint.domain.useCases.user

class UserUseCase(private val userRepository: UserRepository) {
    fun getUser() = userRepository.getCurrentUser()
}