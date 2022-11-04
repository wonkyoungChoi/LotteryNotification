package com.wk.data.remote.services.lottery

import com.wk.data.remote.responses.LotteryInfoResponse
import retrofit2.Response
import retrofit2.http.GET


interface LotteryInfoApi {
    @GET("v1/user/info")
    suspend fun getLotteryInfo(): Response<LotteryInfoResponse>
}
