package com.wk.data.mappers

import com.wk.data.remote.responses.LotteryInfoResponse
import com.wk.domain.models.ui.LotteryInfoList
import com.wk.domain.models.ui.LotteryInfoModel
import com.wk.domain.models.ui.LotteryNumData
import org.jsoup.Jsoup
import java.util.regex.Pattern

fun LotteryInfoResponse.toMapper(): LotteryInfoModel  {

    val contentArray: ArrayList<LotteryInfoList> = arrayListOf()

    val body = body.string()
    val doc = body.let { dataToDoc -> Jsoup.parse(dataToDoc) }
    val lottoNumberData = doc.select("meta[id=desc]").first()?.attr("content")

    val contentData = doc.select("table tbody tr")
    for(data in contentData) {
        val element = data.select("td")
        val rank  = element[0].text() //순위 : 1등, 2등...
        val money = element[3].text() //당첨마다 받는 돈
        val winner = element[2].text() //당첨된 사람의 수
        contentArray.add(LotteryInfoList(rank, money, winner))
    }

    return LotteryInfoModel(
        lotteryRound = getLotteryRoundParsing(lottoNumberData),
        lotteryNumData = getNumber(getLotteryNumberParsing(lottoNumberData), getBonusNumberParsing(lottoNumberData)),
        lotteryInfoList = contentArray
    )
}

fun getLotteryRoundParsing(parsingData: String?): String {
    val pattern = Pattern.compile("[0-9]+회")
    val matcher = parsingData?.let { pattern.matcher(it) }

    return if(matcher?.find() == true) {
        val findData = matcher.group()
        findData.substring(0, findData.length - 1)
    } else ""
}

fun getLotteryNumberParsing(parsingData: String?): String {
    val pattern = Pattern.compile("[0-9]+,[0-9]+,[0-9]+,[0-9]+,[0-9]+,[0-9]+")
    val matcher = parsingData?.let { pattern.matcher(it) }

    return if(matcher?.find() == true) {
        matcher.group()
    } else ""
}

fun getNumber(numberData: String?, bonusData: String?): LotteryNumData {
    val numbers = numberData?.split(",")
    return if(numbers?.size == 6) LotteryNumData(
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

fun getBonusNumberParsing(parsingData: String?): String {
    val pattern = Pattern.compile("\\+[0-9]+")
    val matcher = parsingData?.let { pattern.matcher(it) }

    return if(matcher?.find() == true) {
        matcher.group()
    } else ""
}
