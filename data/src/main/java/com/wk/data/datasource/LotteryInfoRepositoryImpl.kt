package com.wk.data.datasource

import com.wk.data.local.AppDataStore
import com.wk.data.mappers.toModel
import com.wk.data.remote.api_handler.ApiHandler
import com.wk.data.remote.services.lottery.LotteryInfoApi
import com.wk.domain.core.Result
import com.wk.domain.models.ui.LotteryInfoModel
import com.wk.domain.repository.LotteryInfoRepository
import javax.inject.Inject

class LotteryInfoRepositoryImpl @Inject constructor(
    private val lotteryInfoApi: LotteryInfoApi,
    private val apiHandler: ApiHandler
) : LotteryInfoRepository {
    override suspend fun getMainLotteryInfo(): Result<LotteryInfoModel> {
        val response =
            apiHandler.handleCall { lotteryInfoApi.getLotteryInfo("main") }


        return response.data?.let { infoResponse ->
            Result.Success(infoResponse.toModel())
        } ?: Result.Error((response.data?.full_data + response.message))
    }
}