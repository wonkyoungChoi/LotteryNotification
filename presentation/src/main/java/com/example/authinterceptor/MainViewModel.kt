package com.example.authinterceptor

import androidx.lifecycle.viewModelScope
import com.example.authinterceptor.base.BaseViewModel
import com.medium.client.common.wrappers.connectivity.NetworkConnectivityManager
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