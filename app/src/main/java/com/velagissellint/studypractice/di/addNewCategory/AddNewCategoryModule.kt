package com.velagissellint.studypractice.di.addNewCategory

import androidx.lifecycle.ViewModel
import com.velagissellint.presentation.ui.addNewCategory.AddNewCategoryViewModel
import com.velagissellint.studypractice.ViewModelKey
import com.velagissellint.studypractice.scopes.AddNewCategoryScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AddNewCategoryModule {
    @AddNewCategoryScope
    @Binds
    @IntoMap
    @ViewModelKey(AddNewCategoryViewModel::class)
    fun bindViewModelFactory(addNewCategoryViewModel: AddNewCategoryViewModel): ViewModel
}