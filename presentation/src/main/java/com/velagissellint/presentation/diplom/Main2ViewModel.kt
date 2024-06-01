package com.velagissellint.presentation.diplom

import androidx.lifecycle.ViewModel
import com.velagissellint.domain.models.User
import com.velagissellint.domain.useCases.user.UserUseCase

class Main2ViewModel(
    private val userUseCase: UserUseCase,
) : ViewModel() {
    fun getUser(): User? {
       val user = userUseCase.getUser()

        return user
    }
}