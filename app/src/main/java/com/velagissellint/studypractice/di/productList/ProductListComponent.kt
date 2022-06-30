package com.velagissellint.studypractice.di.productList

import com.velagissellint.presentation.containersDi.LogInContainer
import com.velagissellint.presentation.containersDi.ProductListContainer
import com.velagissellint.studypractice.di.dependencies.DependenciesModule
import com.velagissellint.studypractice.scopes.LogInScope
import com.velagissellint.studypractice.scopes.ProductListScope
import dagger.Subcomponent

@ProductListScope
@Subcomponent(modules = [ProductListModule::class, DependenciesModule::class])
interface ProductListComponent : ProductListContainer