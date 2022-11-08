package com.wk.lotteryNotification.home

import com.wk.domain.core.Result
import com.wk.domain.models.ui.LotteryInfoList
import com.wk.domain.models.ui.LotteryInfoModel
import com.wk.domain.models.ui.LotteryNumData

sealed class HomeEvent {
    object DoneButtonClicked : HomeEvent()
    object LoginButtonClicked : HomeEvent()
}

sealed class HomeSideEffect {
    object NavigateToLoginScreen : HomeSideEffect()
}

data class HomeViewState(
    var dataState: Result<LotteryInfoModel> = Result.Loading(),
    val lotteryRound: String = "",
    val lotteryNumData: LotteryNumData = LotteryNumData(),
    var lotteryInfoList: ArrayList<LotteryInfoList> = arrayListOf()
)