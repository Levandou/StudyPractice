package com.velagissellint.studypractice.di.logIn

import com.velagissellint.presentation.containersDi.LogInContainer
import com.velagissellint.studypractice.di.dependencies.DependenciesModule
import com.velagissellint.studypractice.scopes.LogInScope
import dagger.Subcomponent

@LogInScope
@Subcomponent(modules = [LogInModule::class, DependenciesModule::class])
interface LogInComponent : LogInContainer