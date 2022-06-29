package com.velagissellint.presentation.containersDi

import com.velagissellint.presentation.ui.addNewCategory.AddNewCategoryFragment

interface AddNewCategoryContainer {
    fun inject(addNewCategoryFragment: AddNewCategoryFragment)
}