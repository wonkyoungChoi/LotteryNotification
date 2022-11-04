package com.example.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class LotteryInfoResponse(
    var profile_url: String? = "",
    var nickname: String = "",
    var description : String? = "",
    var private_id : String = "",
    var email : String = ""
)