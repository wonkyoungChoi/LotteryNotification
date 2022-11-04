package com.example.domain.models.requests

data class LotteryInfoRequest(
    val username: String,
    val password: String,
    val grant_type: String
)