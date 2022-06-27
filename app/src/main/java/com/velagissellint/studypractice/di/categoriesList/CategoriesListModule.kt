package com.velagissellint.studypractice.di.categoriesList

import androidx.lifecycle.ViewModel
import com.velagissellint.presentation.ui.home.CategoriesListViewModel
import com.velagissellint.studypractice.ViewModelKey
import com.velagissellint.studypractice.scopes.CategoriesListScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CategoriesListModule {
    @CategoriesListScope
    @Binds
    @IntoMap
    @ViewModelKey(CategoriesListViewModel::class)
    fun bindViewModelFactory(categoriesListViewModel: CategoriesListViewModel): ViewModel
}
