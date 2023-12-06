package com.wk.lotteryNotification

import androidx.lifecycle.viewModelScope
import com.wk.data.common.wrappers.NetworkConnectivityManager
import com.wk.lotteryNotification.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkConnectivityManager: NetworkConnectivityManager
) : BaseViewModel<MainViewState, Unit, Unit>(MainViewState()) {

    init {
        viewModelScope.launch {
            networkConnectivityManager.observeNetworkStatus().collect { networkStatus ->
                setState { copy(networkStatus = networkStatus) }
            }
        }
    }

    override fun onEvent(event: Unit) = Unit
}