package com.velagissellint.presentation.diplom.logIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.velagissellint.domain.models.User
import com.velagissellint.domain.network.RequestResult
import com.velagissellint.domain.useCases.logIn.LogInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LogInViewModel(private val logInUseCase: LogInUseCase) : ViewModel() {
    val loginResult = MutableStateFlow<RequestResult<Boolean>>(RequestResult.Loading)
    val isAdminResult = MutableStateFlow<RequestResult<Boolean>>(RequestResult.Loading)

    var user: User? = null

    fun logIn(email: String, password: String) {
        val result: MutableStateFlow<RequestResult<Boolean>> = logInUseCase.logIn(email, password)
        viewModelScope.launch(Dispatchers.IO) {
            result.collectLatest {
                loginResult.value = it
            }
        }
    }

    fun saveUser(user: User) {
        this.user = user
        logInUseCase.saveUser(user)
    }

    fun updateUser(user: User) {
        this.user = user
        logInUseCase.update(user)
    }

    fun loadIsAdmin(email: String) {
        val result: MutableStateFlow<RequestResult<Boolean>> = logInUseCase.loadIsAdmin(email)
        viewModelScope.launch(Dispatchers.IO) {
            result.collectLatest {
                isAdminResult.value = it
            }
        }
    }
}