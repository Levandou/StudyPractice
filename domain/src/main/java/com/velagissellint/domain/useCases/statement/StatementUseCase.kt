package com.velagissellint.domain.useCases.statement

import com.velagissellint.domain.models.diplom.Statement
import com.velagissellint.domain.network.RequestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class StatementUseCase(private val statementRepository: StatementRepository) {
    fun getStatement(profileId: String): Flow<RequestResult<Statement>> =
        statementRepository.getStatement(profileId)

    fun saveStatement(profileId: String, newPosition: String, text: String) =
        statementRepository.createStatement(profileId, newPosition, text)
}