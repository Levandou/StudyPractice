package com.velagissellint.presentation.containersDi

import com.velagissellint.presentation.ui.register.RegisterFragment

interface RegisterContainer {
    fun inject(registerFragment: RegisterFragment)
}