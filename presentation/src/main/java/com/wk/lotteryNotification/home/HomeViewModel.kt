package com.wk.lotteryNotification.home

import androidx.lifecycle.viewModelScope
import com.wk.domain.core.Result
import com.wk.domain.usecase.GetLotteryInfoUseCase
import com.wk.lotteryNotification.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserInfoUseCase: GetLotteryInfoUseCase
): BaseViewModel<HomeViewState, HomeEvent, HomeSideEffect>(HomeViewState()){

    init {
        setState { copy(dataState = Result.Loading())}
        viewModelScope.launch {
            val result = getUserInfoUseCase.invoke()

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
            is HomeEvent.LoginButtonClicked, HomeEvent.DoneButtonClicked -> {
//                setState { copy(userInfoResult = Result.Loading()) }
                viewModelScope.launch {
                    val result = getUserInfoUseCase.invoke()

//                    setState { copy(username = result.data!!.private_id) }

//                    if (result is Result.Success) {
//                        Log.d("HomerViewModel", "Result.Success")
//                        _sideEffects.trySend(HomeSideEffect.NavigateToLoginScreen)
//                    }
                }
            }
        }
    }
}