package com.velagissellint.domain.useCases.addNewCategory

import javax.inject.Inject

class AddNewCategoryUseCase @Inject constructor(private val addNewCategoryRepository: AddNewCategoryRepository) {
    fun addNewCategory(nameCategory: String) {
        addNewCategoryRepository.addNewCategory(nameCategory)
    }
}