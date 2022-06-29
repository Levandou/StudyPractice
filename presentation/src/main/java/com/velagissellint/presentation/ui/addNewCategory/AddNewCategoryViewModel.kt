package com.velagissellint.presentation.ui.addNewCategory

import androidx.lifecycle.ViewModel
import com.velagissellint.domain.useCases.addNewCategory.AddNewCategoryUseCase
import javax.inject.Inject

class AddNewCategoryViewModel @Inject constructor(
    private val addNewCategoryUseCase: AddNewCategoryUseCase
) : ViewModel() {
    fun addNewCategory(nameCategory: String) {
        addNewCategoryUseCase.addNewCategory(nameCategory)
    }

}