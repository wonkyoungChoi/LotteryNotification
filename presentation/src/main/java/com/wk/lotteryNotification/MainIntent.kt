package com.wk.lotteryNotification

import com.medium.client.common.wrappers.connectivity.NetworkStatus

data class MainViewState(
    val networkStatus: NetworkStatus = NetworkStatus.CONNECTED
)