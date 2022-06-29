package com.velagissellint.studypractice.di.dependencies

import com.velagissellint.data.RepositoryImpl
import com.velagissellint.data.firebase.CategoryRepository
import com.velagissellint.data.firebase.UserRepository
import com.velagissellint.domain.useCases.addNewCategory.AddNewCategoryRepository
import com.velagissellint.domain.useCases.category.GetCategoryPageRepository
import com.velagissellint.domain.useCases.logIn.LogInRepository
import com.velagissellint.domain.useCases.registration.CreateAccountRepository
import dagger.Module
import dagger.Provides

@Module
class DependenciesModule {
    @Provides
    fun provideCreateAccountRepository(
        userRepository: UserRepository,
        categoryRepository: CategoryRepository
    ): CreateAccountRepository = RepositoryImpl(userRepository, categoryRepository)

    @Provides
    fun provideLogInRepository(
        userRepository: UserRepository,
        categoryRepository: CategoryRepository
    ): LogInRepository = RepositoryImpl(userRepository, categoryRepository)

    @Provides
    fun provideAddCategoryRepository(
        userRepository: UserRepository,
        categoryRepository: CategoryRepository
    ): AddNewCategoryRepository = RepositoryImpl(userRepository, categoryRepository)

    @Provides
    fun provideGetCategoryPage(
        userRepository: UserRepository,
        categoryRepository: CategoryRepository
    ): GetCategoryPageRepository = RepositoryImpl(userRepository, categoryRepository)
}