package com.example.data

sealed class RequestResult {
    class InProgress: RequestResult()

    class Success<E: Any>(
        val data: E
    ): RequestResult()

    class Error<E: Throwable>(
        val error: E
    ): RequestResult()
}

internal fun <T: Any> Result<T>.toRequestResult(): RequestResult {
    return when {
        isSuccess -> {
            RequestResult.Success(getOrThrow())
        }
        isFailure -> {
            val error = exceptionOrNull()!!
            RequestResult.Error(error)
        }
        else -> error("Impossible branch")
    }
}