package com.velagissellint.presentation.ui.register

import androidx.lifecycle.ViewModel
import com.velagissellint.domain.models.User
import com.velagissellint.domain.useCases.registration.CreateAccountUseCase
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val createAccountUseCase: CreateAccountUseCase
) : ViewModel() {
    fun createAccount(email: String, password: String, user: User) {
        createAccountUseCase.createAccount(email, password, user)
    }
}