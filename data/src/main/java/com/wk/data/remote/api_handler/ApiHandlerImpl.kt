package com.wk.data.remote.api_handler

import com.wk.domain.core.Result
import retrofit2.Response
import javax.inject.Inject

class ApiHandlerImpl @Inject constructor() : ApiHandler {
    override suspend fun <T> handleCall(apiCall: suspend () -> Response<T>): Result<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return Result.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    override suspend fun <T> handleSocket(call: suspend () -> T): Result<T>  =
        try {
            val response = call()
            Result.Success(response)
        } catch (e: Exception) {
            error(e.message ?: e.toString())
        }


    private fun <T> error(errorMessage: String): Result<T> =
        Result.Error("Api call failed $errorMessage")
}