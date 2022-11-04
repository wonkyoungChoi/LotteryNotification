package com.wk.domain.repository

import com.wk.domain.core.Result
import com.wk.domain.models.ui.LotteryInfoModel

interface LotteryInfoRepository {
    suspend fun getLotteryInfo(): Result<LotteryInfoModel>
}