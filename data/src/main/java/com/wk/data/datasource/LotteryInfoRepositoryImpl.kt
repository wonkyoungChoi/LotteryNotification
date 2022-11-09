package com.wk.data.datasource

import com.wk.data.mappers.toMapper
import com.wk.data.remote.api_handler.ApiHandler
import com.wk.data.remote.responses.LotteryInfoResponse
import com.wk.data.remote.services.lottery.LotteryInfoApi
import com.wk.domain.core.Result
import com.wk.domain.models.ui.LotteryInfoModel
import com.wk.domain.repository.LotteryInfoRepository
import javax.inject.Inject

class LotteryInfoRepositoryImpl @Inject constructor(
    private val lotteryInfoApi: LotteryInfoApi,
    private val apiHandler: ApiHandler
) : LotteryInfoRepository {
    override suspend fun getLotteryInfo(type: String): Result<LotteryInfoModel> {

        val response =
            apiHandler.handleSocket { lotteryInfoApi.getLotteryInfo(type) }

        return response.data?.body().let { infoResponse ->
            infoResponse?.let {
                Result.Success(LotteryInfoResponse(it).toMapper())
            }
        } ?: Result.Error((response.data?.errorBody().toString() + response.message))
    }

    override suspend fun getLotterySearchInfo(
        type: String,
        drwNo: String
    ): Result<LotteryInfoModel> {
        val response =
            apiHandler.handleSocket { lotteryInfoApi.getLotteryRoundSearch(type, drwNo) }

        return response.data?.body().let { infoResponse ->
            infoResponse?.let {
                Result.Success(LotteryInfoResponse(it).toMapper())
            }
        } ?: Result.Error((response.data?.errorBody().toString() + response.message))
    }
}