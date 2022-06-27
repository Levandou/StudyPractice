package com.velagissellint.studypractice.di.logIn

import androidx.lifecycle.ViewModel
import com.velagissellint.presentation.ui.logIn.LogInViewModel
import com.velagissellint.studypractice.ViewModelKey
import com.velagissellint.studypractice.scopes.LogInScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LogInModule {
    @LogInScope
    @Binds
    @IntoMap
    @ViewModelKey(LogInViewModel::class)
    fun bindViewModelFactory(logInViewModel: LogInViewModel): ViewModel
}