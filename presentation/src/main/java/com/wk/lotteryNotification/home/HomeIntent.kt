package com.wk.lotteryNotification.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.wk.domain.core.Result
import com.wk.domain.models.ui.LotteryInfoList
import com.wk.domain.models.ui.LotteryInfoModel
import com.wk.domain.models.ui.LotteryNumData

sealed class HomeEvent {
    data class RoundButtonTextChanged(val type: String, val round: String) : HomeEvent()
    data class TypeButtonTextChanged(val type: String) : HomeEvent()
    object SelectRoundButtonClicked : HomeEvent()
    object SelectTypeButtonClicked : HomeEvent()
}

sealed class HomeSideEffect {
    object NavigateToLoginScreen : HomeSideEffect()
}

data class HomeViewState(
    val type : String = Type.MAIN.key,
    val roundSelected : MutableState<Boolean> = mutableStateOf(false),
    val typeSelected : MutableState<Boolean> = mutableStateOf(false),
    var dataState: Result<LotteryInfoModel> = Result.Loading(),
    val totalRound: String = "",
    val lotteryRound: String = "",
    val lotteryNumData: LotteryNumData = LotteryNumData(),
    var lotteryInfoList: ArrayList<LotteryInfoList> = arrayListOf(),
    val lotteryDate: String = ""
)

enum class Type(val key: String) {
    MAIN("main"),
    PENSION("pension")
}
