package com.wk.data.mappers

import android.util.Log
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
    val lottoNumberData = doc.select("meta[id=desc]").first()?.attr("content")!!


    //로또 당첨관련 정보
    val contentData = doc.select("table tbody tr")
    for(i in contentData.indices) {
//        <td>1등</td>
//        <td class="tar"><strong class="color_key1">25,360,314,752원</strong></td>
//        <td>16</td>
//        <td class="tar">1,585,019,672원</td>
//        <td>당첨번호 <strong class="length">6개</strong> 숫자일치</td>
//        <td rowspan="5"> 1등<br> 자동15<br> 반자동1 </td>
        val element = contentData[i].select("td")
        val rank  = element[0].text() //순위 : 1등, 2등...
        val totalMoney = element[1].text() //총 당첨금액
        val winner = element[2].text() // 당첨된 사람의 수
        val money = element[3].text() // 당첨마다 받는 돈
        if(i == 0) {
            val type =  element[5].text() // 번호 뽑은 타입(자동, 반자동, 수동)
            contentArray.add(LotteryInfoList(getLotteryTypeParsing(type), totalMoney, rank, money, winner))
        } else {
            contentArray.add(LotteryInfoList(null, null, rank, money, winner))
        }
    }

    //날짜 값
    var date = ""
    val data = doc.select ("div[class=win_result] p[class=desc]")
    data.first()?.text()?.let {
        date = it.replace("(", "")
        date = date.replace(")", "")
    }

    return LotteryInfoModel(
        lotteryRound = getLotteryRoundParsing(lottoNumberData),
        lotteryNumData = getNumber(getLotteryNumberParsing(lottoNumberData), getBonusNumberParsing(lottoNumberData)),
        lotteryInfoList = contentArray,
        lotteryDate = date
    )
}

private fun getLotteryTypeParsing(type: String): ArrayList<String> {
    val typeList = arrayListOf<String>()
    val types = type.split(" ")
    for(i in types.indices) {
        with(types[i]) {
            when {
                contains("자동") -> {
                    val parseType = typeParser(types[i], "자동")
                    typeList.add(parseType)
                }
                contains("반자동") -> {
                    val parseType = typeParser(types[i], "반자동")
                    typeList.add(parseType)
                }
                contains("수동") -> {
                    val parseType = typeParser(types[i], "수동")
                    typeList.add(parseType)
                }
                else -> {}
            }
        }

    }
    return typeList
}

private fun typeParser(type: String, replaceWord: String): String {
    return type.replace(replaceWord, "$replaceWord : ") + "회"
}

private fun getLotteryRoundParsing(parsingData: String?): String {
    val pattern = Pattern.compile("[0-9]+회")
    val matcher = parsingData?.let { pattern.matcher(it) }

    return if(matcher?.find() == true) {
        val findData = matcher.group()
        findData.substring(0, findData.length - 1)
    } else ""
}

private fun getLotteryNumberParsing(parsingData: String?): String {
    val pattern = Pattern.compile("[0-9]+,[0-9]+,[0-9]+,[0-9]+,[0-9]+,[0-9]+")
    val matcher = parsingData?.let { pattern.matcher(it) }

    return if(matcher?.find() == true) {
        matcher.group()
    } else ""
}

private fun getNumber(numberData: String?, bonusData: String?): LotteryNumData {
    val numbers = numberData?.split(",")
    return if (bonusData != null && numbers?.size == 6) {
            LotteryNumData(
            numbers[0],
            numbers[1],
            numbers[2],
            numbers[3],
            numbers[4],
            numbers[5],
            bonusData
        )
    } else LotteryNumData()
}

private fun getBonusNumberParsing(parsingData: String?): String {
    val pattern = Pattern.compile("\\+[0-9]+")
    val matcher = parsingData?.let { pattern.matcher(it) }

    return if(matcher?.find() == true) {
        matcher.group()
    } else ""
}
