package com.velagissellint.presentation.containersDi

import com.velagissellint.presentation.ui.logIn.LogInFragment

interface LogInContainer {
    fun inject(logInFragment: LogInFragment)
}