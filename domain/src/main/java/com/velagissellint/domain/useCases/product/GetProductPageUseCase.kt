package com.velagissellint.domain.useCases.product

import javax.inject.Inject

class GetProductPageUseCase @Inject constructor(
    private val getProductPageRepository: GetProductPageRepository
) {
    fun getProductPage(id: String) = getProductPageRepository.getProductPage(id)
}