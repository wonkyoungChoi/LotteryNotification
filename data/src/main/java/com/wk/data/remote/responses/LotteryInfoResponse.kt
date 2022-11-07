package com.wk.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class LotteryInfoResponse(
    var full_data: String? = ""
)