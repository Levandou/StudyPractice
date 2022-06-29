package com.velagissellint.presentation.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.velagissellint.domain.models.Category
import com.velagissellint.domain.useCases.category.GetCategoryPageUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriesListViewModel @Inject constructor(
    private val categoryPageUseCase: GetCategoryPageUseCase
) : ViewModel() {
    //private val mutableToDoListPaging = MutableLiveData<PagingData<Case>>()

    fun loadCategoryList(): Flow<PagingData<Category>> {
        return categoryPageUseCase.getCategoryPage().cachedIn(viewModelScope)
    }

}