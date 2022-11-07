package com.wk.data.datasource

import com.wk.data.mappers.toMapper
import com.wk.data.remote.api_handler.ApiHandler
import com.wk.data.remote.responses.LotteryInfoResponse
import com.wk.data.remote.services.lottery.LotteryInfoApi
import com.wk.domain.core.Result
import com.wk.domain.models.ui.LotteryInfoList
import com.wk.domain.models.ui.LotteryInfoModel
import com.wk.domain.repository.LotteryInfoRepository
import org.jsoup.Jsoup
import javax.inject.Inject

class LotteryInfoRepositoryImpl @Inject constructor(
    private val lotteryInfoApi: LotteryInfoApi,
    private val apiHandler: ApiHandler
) : LotteryInfoRepository {
    override suspend fun getMainLotteryInfo(): Result<LotteryInfoModel> {
        val response =
            apiHandler.handleSocket { lotteryInfoApi.getLotteryInfo("byWin") }
        var parseResponse =  LotteryInfoResponse("")
        var contentArray: ArrayList<LotteryInfoList> = arrayListOf()
        response.data.let {
            if(it?.isSuccessful == true) {
                val body = response.data?.body()?.string()
                val doc = body?.let { dataToDoc -> Jsoup.parse(dataToDoc) }
                val lottoNumberData = doc?.select("meta[id=desc]")?.first()?.attr("content")
                lottoNumberData?.let { parseResponse.data = it }
                val contentData = doc?.select("table tbody tr")
                if (contentData != null) {
                    for(data in contentData) {
                        val element = data.select("td")
                        val rank  = element[0].text() //순위 : 1등, 2등...
                        val money = element[3].text() //당첨마다 받는 돈
                        val winner = element[2].text() //당첨된 사람의 수
                        contentArray.add(LotteryInfoList(rank, money, winner))
                    }
                }

            }
        }

        return Result.Success(parseResponse.toMapper(contentArray))
    }
}