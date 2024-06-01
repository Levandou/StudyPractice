package com.velagissellint.domain.useCases.statement

import com.velagissellint.domain.models.diplom.Statement
import com.velagissellint.domain.network.CallbackResult
import com.velagissellint.domain.network.RequestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface StatementRepository {
    fun createStatement(
        profileId: String,
        text: String,
        newPosition: String
    )

    fun getStatement(profileId: String): MutableStateFlow<RequestResult<Statement>>
}