package com.velagissellint.studypractice.di.productList

import androidx.lifecycle.ViewModel
import com.velagissellint.presentation.ui.logIn.LogInViewModel
import com.velagissellint.presentation.ui.productList.ProductListViewModel
import com.velagissellint.studypractice.ViewModelKey
import com.velagissellint.studypractice.scopes.LogInScope
import com.velagissellint.studypractice.scopes.ProductListScope
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ProductListModule {
    @ProductListScope
    @Binds
    @IntoMap
    @ViewModelKey(ProductListViewModel::class)
    fun bindViewModelFactory(productListViewModel: ProductListViewModel): ViewModel
}