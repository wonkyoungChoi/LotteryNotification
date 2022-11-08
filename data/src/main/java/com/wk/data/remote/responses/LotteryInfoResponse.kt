package com.wk.data.remote.responses

import kotlinx.serialization.Serializable
import okhttp3.ResponseBody

@Serializable
data class LotteryInfoResponse(
    var body: ResponseBody
)
