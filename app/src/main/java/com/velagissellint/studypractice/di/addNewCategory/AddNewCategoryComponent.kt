package com.velagissellint.studypractice.di.addNewCategory

import com.velagissellint.presentation.containersDi.AddNewCategoryContainer
import com.velagissellint.presentation.containersDi.LogInContainer
import com.velagissellint.studypractice.di.dependencies.DependenciesModule
import com.velagissellint.studypractice.scopes.AddNewCategory
import com.velagissellint.studypractice.scopes.LogInScope
import dagger.Subcomponent

@AddNewCategory
@Subcomponent(modules = [AddNewCategoryModule::class, DependenciesModule::class])
interface AddNewCategoryComponent : AddNewCategoryContainer