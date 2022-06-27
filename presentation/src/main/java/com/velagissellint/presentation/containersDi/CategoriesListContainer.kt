package com.velagissellint.presentation.containersDi

import com.velagissellint.presentation.ui.home.CategoriesListFragment

interface CategoriesListContainer {
    fun inject(categoriesListFragment: CategoriesListFragment)
}