package com.velagissellint.presentation.containersDi

import com.velagissellint.presentation.ui.deliveries.DeliveriesFragment
import com.velagissellint.presentation.ui.logIn.LogInFragment

interface DeliveriesContainer {
    fun inject(deliveriesFragment: DeliveriesFragment)
}