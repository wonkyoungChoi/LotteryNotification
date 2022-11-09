package com.wk.data.remote.services.lottery

import com.wk.data.remote.responses.LotteryInfoResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query


interface LotteryInfoApi {
    @FormUrlEncoded
    @POST("gameResult.do/")
    suspend fun getLotteryInfo(@Field("method") main: String): Response<ResponseBody>

    @FormUrlEncoded
    @POST("gameResult.do/")
    suspend fun getLotteryRoundSearch(@Field("method") main: String, @Query("drwNo") drwNo: String): Response<ResponseBody>
}
