package com.example.authinterceptor

import com.medium.client.common.wrappers.connectivity.NetworkStatus

data class MainViewState(
    val networkStatus: NetworkStatus = NetworkStatus.CONNECTED
)