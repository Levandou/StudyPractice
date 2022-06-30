package com.velagissellint.presentation.ui.deliveries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.velagissellint.domain.models.Product
import com.velagissellint.domain.useCases.product.GetProductPageUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeliveriesViewModel @Inject constructor(
    private val getProductPageUseCase: GetProductPageUseCase
) : ViewModel() {
    fun loadCategoryList(): Flow<PagingData<Product>> {
        return getProductPageUseCase.getProductPage("nothing").cachedIn(viewModelScope)
    }
}