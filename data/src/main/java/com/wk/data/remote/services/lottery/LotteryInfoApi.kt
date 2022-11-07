package com.wk.data.remote.services.lottery

import com.wk.data.remote.responses.LotteryInfoResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface LotteryInfoApi {
    @FormUrlEncoded
    @POST("common.do/")
    suspend fun getLotteryInfo(@Field("method") main: String): Response<LotteryInfoResponse>
}
