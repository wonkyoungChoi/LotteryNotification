package com.wk.lotteryNotification.main

import com.wk.data.common.wrappers.NetworkStatus

data class MainViewState(
    val networkStatus: NetworkStatus = NetworkStatus.CONNECTED
)