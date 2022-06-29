package com.velagissellint.domain.useCases.category

import javax.inject.Inject

class GetCategoryPageUseCase @Inject constructor(
    private val getCategoryPageRepository: GetCategoryPageRepository
) {
    fun getCategoryPage() = getCategoryPageRepository.getCategoryPage()
}