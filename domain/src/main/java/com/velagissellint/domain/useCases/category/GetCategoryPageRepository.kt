package com.velagissellint.domain.useCases.category

import androidx.paging.PagingData
import com.velagissellint.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface GetCategoryPageRepository {
    fun getCategoryPage(): Flow<PagingData<Category>>
}