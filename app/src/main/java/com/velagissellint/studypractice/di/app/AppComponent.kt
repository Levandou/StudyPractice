package com.velagissellint.studypractice.di.app

import android.app.Application
import com.velagissellint.presentation.containersDi.AddNewCategoryContainer
import com.velagissellint.presentation.containersDi.AppContainer
import com.velagissellint.studypractice.App
import com.velagissellint.studypractice.di.addNewCategory.AddNewCategoryComponent
import com.velagissellint.studypractice.di.categoriesList.CategoriesListComponent
import com.velagissellint.studypractice.di.logIn.LogInComponent
import com.velagissellint.studypractice.di.register.RegisterComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AppContainer {
    override fun plusCategoriesListComponent(): CategoriesListComponent

    override fun plusLogInComponent(): LogInComponent

    override fun plusRegisterComponent(): RegisterComponent

    override fun plusAddNewCategoryComponent(): AddNewCategoryComponent

    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}
