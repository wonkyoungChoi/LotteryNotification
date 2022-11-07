package com.wk.data.mappers

import com.wk.data.remote.responses.LotteryInfoResponse
import com.wk.domain.models.ui.LotteryInfoModel

fun LotteryInfoResponse.toModel(): LotteryInfoModel =
    LotteryInfoModel(
        data = full_data
    )