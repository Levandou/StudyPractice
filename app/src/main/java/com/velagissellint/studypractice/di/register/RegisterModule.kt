package com.velagissellint.studypractice.di.register

import androidx.lifecycle.ViewModel
import com.velagissellint.presentation.ui.register.RegisterViewModel
import com.velagissellint.studypractice.ViewModelKey
import com.velagissellint.studypractice.scopes.RegisterScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface RegisterModule {
    @RegisterScope
    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    fun bindViewModelFactory(registerViewModel: RegisterViewModel): ViewModel
}