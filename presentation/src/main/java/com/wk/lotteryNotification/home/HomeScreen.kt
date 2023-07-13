package com.wk.lotteryNotification.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.wk.domain.core.Result
import com.wk.domain.models.ui.LotteryNumData
import com.wk.lotteryNotification.R
import com.wk.lotteryNotification.ui.*
import com.wk.lotteryNotification.ui.main.InputSelectRoundDialogView
import com.wk.lotteryNotification.ui.main.InputSelectTypeDialogView
import com.wk.lotteryNotification.ui.main.LotteryCircleText
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

    LaunchedEffect(homeViewModel.sideEffects) {
        homeViewModel.sideEffects.collect { sideEffect ->
//            when (sideEffect) {
//                is LoginSideEffect.NavigateToHomeScreen -> navigator.navigate(HomeScreenDestination)
//            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (viewState.dataState) {
                is Result.Success -> {
                    if(viewState.typeSelected.value) InputSelectTypeDialogView(type = viewState.type, viewState = viewState, onClick = {
                        homeViewModel.onEvent(HomeEvent.TypeButtonTextChanged(it))
                    })
                    Log.d("RoundCheck", viewState.type)
                    if(viewState.roundSelected.value) InputSelectRoundDialogView(round = viewState.totalRound, viewState = viewState, onClick = {
                        Log.d("RoundCheck", it)
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