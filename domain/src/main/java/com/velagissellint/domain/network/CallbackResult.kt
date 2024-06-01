package com.velagissellint.domain.network

interface CallbackResult<T> {
    fun onSuccess(t:T)
    fun onError(message: String)
}