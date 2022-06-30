package com.velagissellint.studypractice.di.addNewCategory

import com.velagissellint.presentation.containersDi.AddNewCategoryContainer
import com.velagissellint.studypractice.di.dependencies.DependenciesModule
import com.velagissellint.studypractice.scopes.AddNewCategoryScope
import dagger.Subcomponent

@AddNewCategoryScope
@Subcomponent(modules = [AddNewCategoryModule::class, DependenciesModule::class])
interface AddNewCategoryComponent : AddNewCategoryContainer