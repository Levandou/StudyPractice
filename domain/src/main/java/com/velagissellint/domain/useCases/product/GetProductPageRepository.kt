package com.velagissellint.domain.useCases.product

import androidx.paging.PagingData
import com.velagissellint.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface GetProductPageRepository {
    fun getProductPage(id: String): Flow<PagingData<Product>>
}