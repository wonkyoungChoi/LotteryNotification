package com.example.data.mappers

import com.example.data.remote.responses.LotteryInfoResponse
import com.example.domain.models.ui.LotteryInfoModel

fun LotteryInfoResponse.toModel(): LotteryInfoModel =
    LotteryInfoModel(
        nickname = nickname,
        description = description,
        email = email,
        private_id = private_id,
        profile_url = profile_url
    )