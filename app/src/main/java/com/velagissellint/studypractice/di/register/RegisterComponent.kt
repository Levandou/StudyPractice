package com.velagissellint.studypractice.di.register

import com.velagissellint.presentation.containersDi.RegisterContainer
import com.velagissellint.studypractice.di.dependencies.DependenciesModule
import com.velagissellint.studypractice.scopes.RegisterScope
import dagger.Subcomponent

@RegisterScope
@Subcomponent(modules = [RegisterModule::class, DependenciesModule::class])
interface RegisterComponent : RegisterContainer