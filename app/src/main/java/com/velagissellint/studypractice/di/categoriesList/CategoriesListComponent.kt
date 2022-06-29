package com.velagissellint.studypractice.di.categoriesList

import com.velagissellint.presentation.containersDi.CategoriesListContainer
import com.velagissellint.studypractice.di.dependencies.DependenciesModule
import com.velagissellint.studypractice.scopes.CategoriesListScope
import dagger.Subcomponent

@CategoriesListScope
@Subcomponent(
    modules = [CategoriesListModule::class, DependenciesModule::class]
)
interface CategoriesListComponent : CategoriesListContainer
