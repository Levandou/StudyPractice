package com.velagissellint.presentation.containersDi

interface AppContainer {
    fun plusCategoriesListComponent(): CategoriesListContainer

    fun plusLogInComponent(): LogInContainer

    fun plusRegisterComponent(): RegisterContainer

    fun plusAddNewCategoryComponent(): AddNewCategoryContainer

    fun plusProductListComponent(): ProductListContainer

    fun plusDeliveriesComponent(): DeliveriesContainer
}
