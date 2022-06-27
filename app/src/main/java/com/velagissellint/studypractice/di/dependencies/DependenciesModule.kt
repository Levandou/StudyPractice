package com.velagissellint.studypractice.di.dependencies

import com.velagissellint.data.RepositoryImpl
import com.velagissellint.data.firebase.UserRepository
import com.velagissellint.domain.useCases.logIn.LogInRepository
import com.velagissellint.domain.useCases.registration.CreateAccountRepository
import dagger.Module
import dagger.Provides

@Module
class DependenciesModule {
    @Provides
    fun provideCreateAccountRepository(
        userRepository: UserRepository
    ): CreateAccountRepository = RepositoryImpl(userRepository)

    @Provides
    fun provideLogInRepository(
        userRepository: UserRepository
    ): LogInRepository = RepositoryImpl(userRepository)
}