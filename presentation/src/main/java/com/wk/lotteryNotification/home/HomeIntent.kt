package com.wk.lotteryNotification.home

import com.wk.domain.core.Result
import com.wk.domain.models.ui.LotteryInfoList
import com.wk.domain.models.ui.LotteryNumData

sealed class HomeEvent {
    object DoneButtonClicked : HomeEvent()
    object LoginButtonClicked : HomeEvent()
}

sealed class HomeSideEffect {
    object NavigateToLoginScreen : HomeSideEffect()
}

data class HomeViewState(
    val lotteryRound: String = "",
    val lotteryNumData: Result<LotteryNumData> = Result.Loading(),
    var lotteryInfoList: ArrayList<LotteryInfoList> = arrayListOf()
)