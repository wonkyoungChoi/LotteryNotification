package com.wk.lotteryNotification.qrscan

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.wk.domain.core.Result
import com.wk.lotteryNotification.home.MessagesError
import com.wk.lotteryNotification.ui.LoadingProgress

@Destination
@Composable
fun QrScanScreen(
    navigator: DestinationsNavigator,
    qrScanViewModel: QrScanViewModel = hiltViewModel()
) {
    val viewState = qrScanViewModel.uiState.collectAsState().value

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(qrScanViewModel.sideEffects) {
        qrScanViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is QrScanSideEffect.NavigateToHomeScreen -> navigator.popBackStack()
                else -> {}
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when (viewState.dataState) {
                is Result.Success -> {
                    if(viewState.qrScanned.value.isNotEmpty()) {
                        loadWebUrl(url = viewState.qrScanned.value)
                    }

                    LotteryQrScanView(qrScanViewModel)
                }
                is Result.Error -> {
                    MessagesError(
                        errorMessage = "ERROR"
                    )
                }
                is Result.Loading -> {
                    LoadingProgress()
                }
            }

        }
    }
}

@Composable
fun LotteryQrScanView(qrScanViewModel: QrScanViewModel) {
    var scanFlag by remember {
        mutableStateOf(false)
    }

    AndroidView(
        factory = { context ->
            CompoundBarcodeView(context).apply {
                val capture = CaptureManager(context as Activity, this)
                capture.initializeFromIntent(context.intent, null)
                this.setStatusText("")
                capture.decode()
                this.decodeContinuous { result ->
                    if (scanFlag) {
                        return@decodeContinuous
                    }
                    println("scanFlag true")
                    scanFlag = true
                    result.text?.let { barCodeOrQr ->
                        qrScanViewModel.onEvent(QrScanEvent.SuccessQrScanned(barCodeOrQr))
                        scanFlag = false
                    }
                    //If you don't put this scanFlag = false, it will never work again.
                    //you can put a delay over 2 seconds and then scanFlag = false to prevent multiple scanning
                }
                this.resume()
            }
        },
        modifier = Modifier
    )
}

@Composable
fun loadWebUrl(url: String) {
    val webViewState = rememberWebViewState(url)
    WebView(
        state = webViewState,
        onCreated = { it.settings.javaScriptEnabled = true }
    )
}