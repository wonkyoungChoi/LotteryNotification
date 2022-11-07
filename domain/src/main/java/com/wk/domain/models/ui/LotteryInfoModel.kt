package com.wk.domain.models.ui

import kotlinx.serialization.Serializable

@Serializable
data class LotteryInfoModel(
    var lotteryRound: String,
    var lotteryNumData: LotteryNumData,
    var lotteryInfoList: ArrayList<LotteryInfoList>
)

@Serializable
data class LotteryNumData(
    var firstNum: String? = "",
    var secondNum: String? = "",
    var thirdNum: String? = "",
    var fourthNum: String? = "",
    var fifthNum: String? = "",
    var sixthNum: String? = "",
    var bonusNum: String? = ""
)

@Serializable
data class LotteryInfoList(
    var rank: String? = "",
    var takeMoney: String? = "",
    var winner: String? = ""
)
