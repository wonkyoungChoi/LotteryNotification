package com.wk.lotteryNotification.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.wk.domain.core.Result
import com.wk.domain.usecase.GetLotteryInfoUseCase
import com.wk.domain.usecase.GetLotterySearchInfoUseCase
import com.wk.lotteryNotification.base.BaseViewModel
import com.wk.lotteryNotification.util.Constants.MAIN_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLotteryInfoUseCase: GetLotteryInfoUseCase,
    private val getLotterySearchInfoUseCase: GetLotterySearchInfoUseCase
): BaseViewModel<HomeViewState, HomeEvent, HomeSideEffect>(HomeViewState()){

    init {
        setState { copy(dataState = Result.Loading())}
        viewModelScope.launch {
            val result = getLotteryInfoUseCase.invoke(MAIN_TYPE)

            if(result.data != null) {
                setState {
                    copy(
                        dataState = result,
                        lotteryRound = result.data!!.lotteryRound,
                        lotteryNumData = result.data!!.lotteryNumData,
                        lotteryInfoList = result.data!!.lotteryInfoList
                    )
                }
            }
        }
    }

    override fun onEvent(event: HomeEvent) {

        when(event) {
            is HomeEvent.SelectRoundButtonClicked -> {
                setState { copy(v = mutableStateOf(true)) }
            }
            is HomeEvent.RoundButtonTextChanged -> {
                setState { copy(dataState = Result.Loading())}
                viewModelScope.launch {
                    //TODO drwNo 선택할 수 있는 팝업? 혹은 무언가 만들기
                    val result = getLotterySearchInfoUseCase.invoke(MAIN_TYPE, getState().lotteryRound)

                    setState {
                        copy(
                            dataState = result,
                            lotteryRound = result.data!!.lotteryRound,
                            lotteryNumData = result.data!!.lotteryNumData,
                            lotteryInfoList = result.data!!.lotteryInfoList
                        )
                    }
                }
                setState { copy(v = mutableStateOf(false)) }

            }
            else -> {}
        }
    }
}