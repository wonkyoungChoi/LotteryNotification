package com.wk.lotteryNotification.qrscan

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.wk.domain.core.Result
import com.wk.domain.models.ui.LotteryInfoList
import com.wk.domain.models.ui.LotteryInfoModel
import com.wk.domain.models.ui.LotteryNumData

sealed class QrScanEvent {
    data class SuccessQrScanned(val url: String) : QrScanEvent()
}

sealed class QrScanSideEffect {
    object NavigateToHomeScreen : QrScanSideEffect()
}

data class QrScanViewState(
    val type : String = Type.MAIN.key,
    val qrScanned : MutableState<String> = mutableStateOf(""),
    var dataState: Result<Boolean> = Result.Loading(),
)

enum class Type(val key: String) {
    MAIN("main"),
    PENSION("pension")
}