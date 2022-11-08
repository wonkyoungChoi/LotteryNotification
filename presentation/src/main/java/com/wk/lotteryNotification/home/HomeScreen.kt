package com.wk.lotteryNotification.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.wk.domain.core.Result
import com.wk.lotteryNotification.R
import com.wk.lotteryNotification.ui.LotteryCirclePlus
import com.wk.lotteryNotification.ui.LotteryCircleText
import com.wk.lotteryNotification.ui.LotteryTableRow
import com.wk.lotteryNotification.ui.LotteryTableTitle
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
            Spacer_10()
            LotteryRoundView(viewState = viewState)
            Spacer_10()
            when (viewState.lotteryNumData) {
                is Result.Success -> {
                    LotteryViews(viewState = viewState)
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
            
            LotteryInfoTableView(viewState = viewState)

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
        Text(text = viewState.lotteryRound)
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
        LotteryCircleText(text = viewState.lotteryNumData.data?.firstNum.toString())
        LotteryCircleText(text = viewState.lotteryNumData.data?.secondNum.toString())
        LotteryCircleText(text = viewState.lotteryNumData.data?.thirdNum.toString())
        LotteryCircleText(text = viewState.lotteryNumData.data?.fourthNum.toString())
        LotteryCircleText(text = viewState.lotteryNumData.data?.fifthNum.toString())
        LotteryCircleText(text = viewState.lotteryNumData.data?.sixthNum.toString())
        LotteryCirclePlus()
        LotteryCircleText(text = viewState.lotteryNumData.data?.bonusNum.toString())
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
fun ColumnScope.LoadingProgress() {
    CircularProgressIndicator(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.size_16))
            .align(Alignment.CenterHorizontally)
    )
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