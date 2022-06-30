package com.velagissellint.studypractice.di.deliveries

import androidx.lifecycle.ViewModel
import com.velagissellint.presentation.ui.deliveries.DeliveriesViewModel
import com.velagissellint.presentation.ui.logIn.LogInViewModel
import com.velagissellint.studypractice.ViewModelKey
import com.velagissellint.studypractice.scopes.DeliveriesScope
import com.velagissellint.studypractice.scopes.LogInScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DeliveriesModule {
    @DeliveriesScope
    @Binds
    @IntoMap
    @ViewModelKey(DeliveriesViewModel::class)
    fun bindViewModelFactory(deliveriesViewModel: DeliveriesViewModel): ViewModel
}