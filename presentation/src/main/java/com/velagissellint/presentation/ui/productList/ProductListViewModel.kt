package com.velagissellint.presentation.ui.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.velagissellint.domain.models.Category
import com.velagissellint.domain.models.Product
import com.velagissellint.domain.useCases.product.GetProductPageUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductListViewModel @Inject constructor(
    private val getProductPageUseCase: GetProductPageUseCase
) : ViewModel() {
    fun loadCategoryList(id: String): Flow<PagingData<Product>> {
        return getProductPageUseCase.getProductPage(id).cachedIn(viewModelScope)
    }
}