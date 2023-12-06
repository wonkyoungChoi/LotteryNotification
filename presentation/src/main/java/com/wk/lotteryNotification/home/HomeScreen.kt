package com.wk.lotteryNotification.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.Intent.CATEGORY_DEFAULT
import android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.wk.domain.core.Result
import com.wk.domain.models.ui.LotteryNumData
import com.wk.lotteryNotification.R
import com.wk.lotteryNotification.destinations.QrScanScreenDestination
import com.wk.lotteryNotification.ui.*
import com.wk.lotteryNotification.ui.main.InputSelectRoundDialogView
import com.wk.lotteryNotification.ui.main.InputSelectTypeDialogView
import com.wk.lotteryNotification.ui.main.LotteryCircleText
import com.wk.lotteryNotification.ui.main.LotteryQrScanButton
import com.wk.lotteryNotification.ui.main.LotteryRoundButton
import com.wk.lotteryNotification.util.Constants

@OptIn(ExperimentalComposeUiApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val viewState = homeViewModel.uiState.collectAsState().value

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            homeViewModel.onEvent(HomeEvent.SelectQrScanButtonClicked)
        } else {
            Toast.makeText(context, "QRCode를 스캔하기 위해서는 카메라 권한이 필요합니다.", Toast.LENGTH_SHORT). show()
        }
    }

    LaunchedEffect(homeViewModel.sideEffects) {
        homeViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is HomeSideEffect.NavigateToQrCheckScreen -> { navigator.navigate(QrScanScreenDestination) }
                is HomeSideEffect.GoToSetting -> { context.openAppSettings() }
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

                    if(viewState.typeSelected.value) InputSelectTypeDialogView(type = viewState.type, viewState = viewState, onClick = {
                        homeViewModel.onEvent(HomeEvent.TypeButtonTextChanged(it))
                    })

                    if(viewState.roundSelected.value) InputSelectRoundDialogView(round = viewState.totalRound, viewState = viewState, onClick = {
                        homeViewModel.onEvent(HomeEvent.RoundButtonTextChanged(viewState.type, it))
                    })

                    LotteryRoundButton(text = viewState.type, onClick = {
                        homeViewModel.onEvent(HomeEvent.SelectTypeButtonClicked)
                    })
                    Spacer_10()
                    LotteryRoundButton(text = viewState.lotteryRound + "회", onClick = {
                        homeViewModel.onEvent(HomeEvent.SelectRoundButtonClicked)
                    })
                    Spacer_10()
                    LotteryQrScanView(homeViewModel = homeViewModel)
                    Spacer_10()
                    LotteryRoundView(viewState = viewState)
                    Spacer_10()
                    LotteryDateView(viewState = viewState)
                    Spacer_10()
                    LotteryViews(viewState = viewState)
                    if(viewState.lotteryBonusNumData != LotteryNumData()) {
                        Spacer_10()
                        LotteryPensionBonusViews(viewState = viewState)
                    }
                    LotteryInfoTableView(viewState = viewState)
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

//private fun initWorker(context: Context) {
//    val request = OneTimeWorkRequestBuilder<NotificationWorker>().build()
//    WorkManager.getInstance(context).enqueue(request)
//}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LotteryQrScanView(homeViewModel: HomeViewModel) {
    val context = LocalContext.current

    // Camera permission state
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA) { isGranted: Boolean ->
        if (isGranted) {
            homeViewModel.onEvent(HomeEvent.SelectQrScanButtonClicked)
        } else {
            Toast.makeText(context, "QRCode를 스캔하기 위해서는 카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }

    LotteryQrScanButton(onClick = {
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
//            == PackageManager.PERMISSION_GRANTED){
//            homeViewModel.onEvent(HomeEvent.SelectQrScanButtonClicked)
//        } else {
//            cameraPermissionState.launchPermissionRequest()
//        }
        if (cameraPermissionState.status.isGranted) {
            homeViewModel.onEvent(HomeEvent.SelectQrScanButtonClicked)
        } else {
            if(cameraPermissionState.status.shouldShowRationale) {
                Toast.makeText(context, "QRCode를 스캔하기 위해서는 카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
                homeViewModel.onEvent(HomeEvent.SelectGoToSetting)
            } else {
                Toast.makeText(context, "QRCode를 스캔하기 위해서는 카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
                cameraPermissionState.launchPermissionRequest()
            }
        }
    })
}

fun Context.openAppSettings() {
    val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", packageName, null)
        addCategory(CATEGORY_DEFAULT)
        addFlags(FLAG_ACTIVITY_NEW_TASK)
        addFlags(FLAG_ACTIVITY_NO_HISTORY)
        addFlags(FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    }

    startActivity(intent)
}

@Composable
fun LotteryRoundView(viewState: HomeViewState) {
    Row(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.size_10))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = viewState.lotteryRound + "회")
    }
}

@Composable
fun LotteryDateView(viewState: HomeViewState) {
    Row(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.size_10))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = viewState.lotteryDate)
    }
}

@Composable
fun LotteryViews(viewState: HomeViewState) {
    Row(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.size_10))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        LotteryCircleText(text = viewState.lotteryNumData.firstNum.toString())
        LotteryCircleText(text = viewState.lotteryNumData.secondNum.toString())
        LotteryCircleText(text = viewState.lotteryNumData.thirdNum.toString())
        LotteryCircleText(text = viewState.lotteryNumData.fourthNum.toString())
        LotteryCircleText(text = viewState.lotteryNumData.fifthNum.toString())
        LotteryCircleText(text = viewState.lotteryNumData.sixthNum.toString())
        if(viewState.lotteryNumData.firstNum?.contains("조") == false) {
            LotteryCirclePlus()
        }
        LotteryCircleText(text = viewState.lotteryNumData.bonusNum.toString())
    }
}

@Composable
fun LotteryPensionBonusViews(viewState: HomeViewState) {
    Row(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.size_10))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        LotteryCircleText(text = viewState.lotteryBonusNumData.firstNum.toString())
        LotteryCircleText(text = viewState.lotteryBonusNumData.secondNum.toString())
        LotteryCircleText(text = viewState.lotteryBonusNumData.thirdNum.toString())
        LotteryCircleText(text = viewState.lotteryBonusNumData.fourthNum.toString())
        LotteryCircleText(text = viewState.lotteryBonusNumData.fifthNum.toString())
        LotteryCircleText(text = viewState.lotteryBonusNumData.sixthNum.toString())
        LotteryCircleText(text = viewState.lotteryBonusNumData.bonusNum.toString())
    }
}



@Composable
fun LotteryInfoTableView(viewState: HomeViewState) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.size_10))
    ) {
        val list = viewState.lotteryInfoList

        LotteryTableTitle(head1 = Constants.RANK, head2 = Constants.TOTAL_MONEY, head3 = Constants.WINNER)

        for(i in list.indices) {
            LotteryTableRow(
                rank = list[i].rank,
                takeMoney = list[i].takeMoney,
                winner = list[i].winner + Constants.PEOPLE_NUM)
        }
    }
}

@Composable
fun Spacer_10() {
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.size_10)))
}

@Composable
fun MessagesError(
    errorMessage: String
) {
    Text(
        text = errorMessage,
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.error
    )
}