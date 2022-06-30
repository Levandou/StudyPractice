package com.velagissellint.studypractice.di.deliveries

import com.velagissellint.presentation.containersDi.DeliveriesContainer
import com.velagissellint.presentation.containersDi.LogInContainer
import com.velagissellint.studypractice.di.dependencies.DependenciesModule
import com.velagissellint.studypractice.scopes.DeliveriesScope
import com.velagissellint.studypractice.scopes.LogInScope
import dagger.Subcomponent

@DeliveriesScope
@Subcomponent(modules = [DeliveriesModule::class, DependenciesModule::class])
interface DeliveriesComponent : DeliveriesContainer