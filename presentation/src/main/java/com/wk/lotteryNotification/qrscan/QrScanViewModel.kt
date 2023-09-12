package com.wk.lotteryNotification.qrscan

import androidx.compose.runtime.mutableStateOf
import androidx.datastore.dataStore
import androidx.lifecycle.viewModelScope
import com.wk.domain.core.Result
import com.wk.lotteryNotification.base.BaseViewModel
import com.wk.lotteryNotification.home.HomeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrScanViewModel @Inject constructor(

): BaseViewModel<QrScanViewState, QrScanEvent, QrScanSideEffect>(QrScanViewState()){

    init {
        setState { copy(dataState = Result.Loading())}
        viewModelScope.launch {
            setState { copy (dataState = Result.Success(true)) }
        }
    }

    override fun onEvent(event: QrScanEvent) {
        when(event) {
            is QrScanEvent.SuccessQrScanned -> {
                setState { copy(qrScanned = mutableStateOf(event.url)) }
            }
        }
    }
}