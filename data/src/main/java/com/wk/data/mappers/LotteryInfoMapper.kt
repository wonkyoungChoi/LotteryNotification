package com.wk.data.mappers

import com.wk.data.remote.responses.LotteryInfoResponse
import com.wk.domain.models.ui.LotteryInfoModel

fun LotteryInfoResponse.toModel(): LotteryInfoModel =
    LotteryInfoModel(
        nickname = nickname,
        description = description,
        email = email,
        private_id = private_id,
        profile_url = profile_url
    )