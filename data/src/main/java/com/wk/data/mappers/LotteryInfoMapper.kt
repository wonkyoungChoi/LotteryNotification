package com.wk.data.mappers

import com.wk.data.remote.responses.LotteryInfoResponse
import com.wk.domain.models.ui.LotteryInfoList
import com.wk.domain.models.ui.LotteryInfoModel
import com.wk.domain.models.ui.LotteryNumData
import java.util.regex.Pattern

fun LotteryInfoResponse.toMapper(list: ArrayList<LotteryInfoList>): LotteryInfoModel  {

    return LotteryInfoModel(
        lotteryRound = getLotteryRoundParsing(data),
        lotteryNumData = getNumber(getLotteryNumberParsing(data), getBonusNumberParsing(data)),
        lotteryInfoList = list
    )
}

fun getLotteryRoundParsing(parsingData: String): String {
    val pattern = Pattern.compile("[0-9]+회")
    val matcher = pattern.matcher(parsingData)

    return if(matcher.find()) {
        val findData = matcher.group()
        findData.substring(0, findData.length - 1)
    } else ""
}

fun getLotteryNumberParsing(parsingData: String): String {
    val pattern = Pattern.compile("[0-9]+,[0-9]+,[0-9]+,[0-9]+,[0-9]+,[0-9]+")
    val matcher = pattern.matcher(parsingData)

    return if(matcher.find()) {
        matcher.group()
    } else ""
}

fun getNumber(numberData: String, bonusData: String): LotteryNumData {
    val numbers = numberData.split(",")
    return if(numbers.size == 6) LotteryNumData(
        numbers[0],
        numbers[1],
        numbers[2],
        numbers[3],
        numbers[4],
        numbers[5],
        bonusData
    )
    else LotteryNumData()
}

fun getBonusNumberParsing(parsingData: String): String {
    val pattern = Pattern.compile("\\+[0-9]+")
    val matcher = pattern.matcher(parsingData)

    return if(matcher.find()) {
        matcher.group()
    } else ""
}
