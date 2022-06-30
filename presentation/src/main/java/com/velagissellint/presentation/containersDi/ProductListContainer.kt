package com.velagissellint.presentation.containersDi

import com.velagissellint.presentation.ui.productList.ProductListFragment

interface ProductListContainer {
    fun inject(productListFragment: ProductListFragment)
}