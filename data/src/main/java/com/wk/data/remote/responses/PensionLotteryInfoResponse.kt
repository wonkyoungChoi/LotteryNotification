package com.wk.data.remote.responses

import kotlinx.serialization.Serializable
import okhttp3.ResponseBody

@Serializable
data class PensionLotteryInfoResponse(
    var body: ResponseBody
)
