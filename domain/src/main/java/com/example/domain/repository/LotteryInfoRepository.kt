package com.example.domain.repository

import com.example.domain.core.Result
import com.example.domain.models.ui.LotteryInfoModel


interface LotteryInfoRepository {

    suspend fun getUserInfo(): Result<LotteryInfoModel>

}