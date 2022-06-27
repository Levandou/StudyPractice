package com.velagissellint.presentation.ui.logIn

import androidx.lifecycle.ViewModel
import com.velagissellint.domain.useCases.logIn.LogInUseCase
import javax.inject.Inject

class LogInViewModel @Inject constructor(private val logInUseCase: LogInUseCase) : ViewModel() {
    fun logIn(email: String, password: String) {
        logInUseCase.logIn(email, password)
    }
}