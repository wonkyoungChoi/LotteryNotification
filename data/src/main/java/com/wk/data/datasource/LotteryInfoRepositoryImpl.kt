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
    private val appDataStore: AppDataStore,
    private val apiHandler: ApiHandler
) : LotteryInfoRepository {
    override suspend fun getLotteryInfo(): Result<LotteryInfoModel> {
        val response =
            apiHandler.handleCall { lotteryInfoApi.getLotteryInfo() }


        return response.data?.let { infoResponse ->
            appDataStore.setLotteryInfo(infoResponse.toModel())
            Result.Success(infoResponse.toModel())
        } ?: Result.Error((response.data?.nickname + response.message))
    }
}