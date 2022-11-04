package com.example.data.remote.api_handler

import com.example.domain.core.Result
import retrofit2.Response

interface ApiHandler {

    suspend fun <T> handleCall(call: suspend () -> Response<T>): Result<T>

//    suspend fun <T> handleSocket(call: suspend () -> T): Result<T>
}