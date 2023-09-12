package com.wk.lotteryNotification.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.wk.domain.core.Result
import com.wk.domain.models.ui.LotteryNumData
import com.wk.domain.usecase.GetLotteryInfoUseCase
import com.wk.domain.usecase.GetLotterySearchInfoUseCase
import com.wk.domain.usecase.GetPensionLotteryInfoUseCase
import com.wk.domain.usecase.GetPensionLotterySearchInfoUseCase
import com.wk.lotteryNotification.base.BaseViewModel
import com.wk.lotteryNotification.util.Constants.MAIN_TYPE
import com.wk.lotteryNotification.util.Constants.PENSION_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLotteryInfoUseCase: GetLotteryInfoUseCase,
    private val getLotterySearchInfoUseCase: GetLotterySearchInfoUseCase,
    private val getPensionLotteryInfoUseCase: GetPensionLotteryInfoUseCase,
    private val getPensionLotterySearchInfoUseCase: GetPensionLotterySearchInfoUseCase
): BaseViewModel<HomeViewState, HomeEvent, HomeSideEffect>(HomeViewState()){

    init {
        setState { copy(dataState = Result.Loading())}
        viewModelScope.launch {
            val result = getLotteryInfoUseCase.invoke(MAIN_TYPE)

            if(result.data != null) {
                setState {
                    copy(
                        dataState = result,
                        totalRound = result.data!!.lotteryRound,
                        lotteryRound = result.data!!.lotteryRound,
                        lotteryNumData = result.data!!.lotteryNumData,
                        lotteryInfoList = result.data!!.lotteryInfoList,
                        lotteryDate = result.data!!.lotteryDate
                    )
                }
            }
        }
    }

    override fun onEvent(event: HomeEvent) {

        when(event) {
            is HomeEvent.SelectTypeButtonClicked -> {
                setState { copy(typeSelected = mutableStateOf(true)) }
            }
            is HomeEvent.SelectRoundButtonClicked -> {
                setState { copy(roundSelected = mutableStateOf(true)) }
            }
            is HomeEvent.SelectQrScanButtonClicked -> {
                _sideEffects.trySend(HomeSideEffect.NavigateToQrCheckScreen)
            }
            is HomeEvent.SelectGoToSetting -> {
                _sideEffects.trySend(HomeSideEffect.GoToSetting)
            }
            is HomeEvent.TypeButtonTextChanged -> {
                setState { copy(dataState = Result.Loading())}
                viewModelScope.launch {
                    when(event.type) {
                        Type.MAIN.key -> {
                            val result = getLotteryInfoUseCase.invoke(MAIN_TYPE)
                            if(result.data != null) {
                                setState {
                                    copy(
                                        dataState = result,
                                        type = event.type,
                                        totalRound = result.data!!.lotteryRound,
                                        lotteryRound = result.data!!.lotteryRound,
                                        lotteryNumData = result.data!!.lotteryNumData,
                                        lotteryBonusNumData = LotteryNumData(),
                                        lotteryInfoList = result.data!!.lotteryInfoList,
                                        lotteryDate = result.data!!.lotteryDate
                                    )
                                }
                            }
                        }
                        Type.PENSION.key -> {
                            val result = getPensionLotteryInfoUseCase.invoke(PENSION_TYPE)
                            if(result.data != null) {
                                setState {
                                    copy(
                                        dataState = result,
                                        type = event.type,
                                        totalRound = result.data!!.lotteryRound,
                                        lotteryRound = result.data!!.lotteryRound,
                                        lotteryNumData = result.data!!.lotteryNumData,
                                        lotteryBonusNumData =  if(result.data!!.bonusNumData != null) {
                                            result.data!!.bonusNumData!!
                                        } else {
                                            LotteryNumData()
                                        },
                                        lotteryInfoList = result.data!!.lotteryInfoList,
                                        lotteryDate = result.data!!.lotteryDate
                                    )
                                }
                            }
                        }
                    }

                }
                setState { copy(typeSelected = mutableStateOf(false)) }

            }
            is HomeEvent.RoundButtonTextChanged -> {
                setState { copy(dataState = Result.Loading())}
                viewModelScope.launch {
                    when(event.type) {
                        Type.MAIN.key -> {
                            val result = getLotterySearchInfoUseCase.invoke(MAIN_TYPE, event.round)

                            if(result.data != null) {
                                setState {
                                    copy(
                                        dataState = result,
                                        lotteryRound = result.data!!.lotteryRound,
                                        lotteryNumData = result.data!!.lotteryNumData,
                                        lotteryBonusNumData = LotteryNumData(),
                                        lotteryInfoList = result.data!!.lotteryInfoList,
                                        lotteryDate = result.data!!.lotteryDate
                                    )
                                }
                            }
                        }
                        Type.PENSION.key -> {
                            val result = getPensionLotterySearchInfoUseCase.invoke(PENSION_TYPE, event.round)

                            if(result.data != null) {
                                setState {
                                    copy(
                                        dataState = result,
                                        lotteryRound = result.data!!.lotteryRound,
                                        lotteryNumData = result.data!!.lotteryNumData,
                                        lotteryBonusNumData =  if(result.data!!.bonusNumData != null) {
                                            result.data!!.bonusNumData!!
                                        } else {
                                            LotteryNumData()
                                        },
                                        lotteryInfoList = result.data!!.lotteryInfoList,
                                        lotteryDate = result.data!!.lotteryDate
                                    )
                                }
                            }
                        }
                    }

                }
                setState { copy(roundSelected = mutableStateOf(false)) }

            }
            else -> {}
        }
    }
}