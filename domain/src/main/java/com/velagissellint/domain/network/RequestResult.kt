package com.velagissellint.domain.network

sealed class RequestResult<out T : Any?> {
    data class Success<out T : Any?>(
        val data: T
    ) : RequestResult<T>()

    data class Error(
        val message: String = ""
    ) : RequestResult<Nothing>()

    data object Loading : RequestResult<Nothing>()
}