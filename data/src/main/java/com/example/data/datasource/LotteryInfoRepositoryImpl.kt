package com.example.data.datasource

import com.example.data.local.AppDataStore
import com.example.data.mappers.toModel
import com.example.data.remote.api_handler.ApiHandler
import com.example.data.remote.services.lottery.LotteryInfoApi
import com.example.domain.core.Result
import com.example.domain.models.ui.LotteryInfoModel
import com.example.domain.repository.LotteryInfoRepository
import javax.inject.Inject

class LotteryInfoRepositoryImpl @Inject constructor(
    private val lotteryInfoApi: LotteryInfoApi,
    private val appDataStore: AppDataStore,
    private val apiHandler: ApiHandler
) : LotteryInfoRepository {
    override suspend fun getUserInfo(): Result<LotteryInfoModel> {
        val response =
            apiHandler.handleCall { lotteryInfoApi.getLotteryInfo() }


        return response.data?.let { infoResponse ->
            appDataStore.setLotteryInfo(infoResponse.toModel())
            Result.Success(infoResponse.toModel())
        } ?: Result.Error((response.data?.nickname + response.message))
    }
}