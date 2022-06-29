package com.velagissellint.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.velagissellint.data.firebase.CategoryRepository
import com.velagissellint.data.firebase.UserRepository
import com.velagissellint.data.paging.PagingSource
import com.velagissellint.domain.models.Category
import com.velagissellint.domain.models.User
import com.velagissellint.domain.useCases.addNewCategory.AddNewCategoryRepository
import com.velagissellint.domain.useCases.category.GetCategoryPageRepository
import com.velagissellint.domain.useCases.logIn.LogInRepository
import com.velagissellint.domain.useCases.registration.CreateAccountRepository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository
) : CreateAccountRepository, LogInRepository, AddNewCategoryRepository,
    GetCategoryPageRepository {
    override fun createAccount(
        email: String,
        password: String,
        user: User
    ) {
        userRepository.createAccount(email, password, user)
    }

    override fun logIn(email: String, password: String) {
        userRepository.logIn(email, password)
    }

    override fun addNewCategory(nameCategory: String) {
        categoryRepository.addCategory(nameCategory)
    }

    override fun getCategoryPage(): Flow<PagingData<Category>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = 20,
                maxSize = 100
            ),
            pagingSourceFactory = { PagingSource() },
        ).flow
}