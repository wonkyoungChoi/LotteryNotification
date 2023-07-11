package com.wk.data.mappers

import android.util.Log
import com.wk.data.remote.responses.PensionLotteryInfoResponse
import com.wk.domain.models.ui.LotteryInfoList
import com.wk.domain.models.ui.LotteryInfoModel
import com.wk.domain.models.ui.LotteryNumData
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.util.regex.Pattern

fun PensionLotteryInfoResponse.toMapper(): LotteryInfoModel  {

    val contentArray: ArrayList<LotteryInfoList> = arrayListOf()

    val body = body.string()
    val doc = body.let { dataToDoc -> Jsoup.parse(dataToDoc) }
    val lottoNumberData = doc.select("meta[id=desc]").first()?.attr("content")!!


    //로또 당첨관련 정보
    val contentData = doc.select("table tbody tr")
    //val lottoNumberData = contentData[0].select("td [class=ta_right]").first()?.attr("content")
    for(i in contentData.indices) {
        val element = contentData[i].select("td")
        val rank  = element[0].text() //순위 : 1등, 2등...
        when(i) {
            0, contentData.size - 1 -> {
                val winner = element[5].text() // 당첨된 사람의 수
                val money = element[4].text() // 당첨마다 받는 돈
                contentArray.add(LotteryInfoList(null, "0", rank, money, winner))
            }
            else -> {
                val winner = element[4].text() // 당첨된 사람의 수
                val money = element[3].text() // 당첨마다 받는 돈
                contentArray.add(LotteryInfoList(null, null, rank, money, winner))
            }
        }
    }

    //날짜 값
    var date = ""
    val data = doc.select ("div[class=win_result al720] p[class=desc]")
    data.first()?.text()?.let {
        date = it.replace("(", "")
        date = date.replace(")", "")
    }

    val numbers = doc.select ("div[class=win_result al720] div[class=win_num_wrap] div[class=win720_num] span span")

    return LotteryInfoModel(
        lotteryRound = getLotteryRoundParsing(lottoNumberData),
        lotteryNumData = getNumber(getNumberParsing(numbers)),
        bonusNumData = getNumber(getBonusNumberParsing(numbers)),
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

    return if (matcher?.find() == true) {
        val findData = matcher.group()
        findData.substring(0, findData.length - 1)
    } else ""
}

private fun getNumber(numbers: ArrayList<String>): LotteryNumData {
    return  if(numbers.size == 7) {
        LotteryNumData(
            numbers[0],
            numbers[1],
            numbers[2],
            numbers[3],
            numbers[4],
            numbers[5],
            numbers[6]
        )
    } else LotteryNumData()
}

private fun getNumberParsing(numbers: Elements): ArrayList<String> {
    val winNums = arrayListOf<String>()
    for(i in 0..6) {
        try {
            if(i == 0) {
                Log.d("NumberCheck", numbers[i].text())
                winNums.add(numbers[i].text() + "조")
            } else {
                winNums.add(numbers[i].text())
            }
        } catch (e: Exception) {
            winNums.add("")
            e.printStackTrace()
        }
    }
    return winNums
}

private fun getBonusNumberParsing(numbers: ArrayList<Element>): ArrayList<String> {
    val bonusNums = arrayListOf<String>()
    for(i in 7..13) {
        try {
            if(i == 7) {
                bonusNums.add(numbers[i].text() + "조")
            } else {
                bonusNums.add(numbers[i].text())
            }
        } catch (e: Exception) {
            bonusNums.add("")
            e.printStackTrace()
        }
    }
    return bonusNums
}
