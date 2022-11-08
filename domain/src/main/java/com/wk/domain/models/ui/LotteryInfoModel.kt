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
    var firstNum: Int? = 0,
    var secondNum: Int? = 0,
    var thirdNum: Int? = 0,
    var fourthNum: Int? = 0,
    var fifthNum: Int? = 0,
    var sixthNum: Int? = 0,
    var bonusNum: Int? = 0
)

@Serializable
data class LotteryInfoList(
    var rank: String = "",
    var takeMoney: String = "",
    var winner: String = ""
)
