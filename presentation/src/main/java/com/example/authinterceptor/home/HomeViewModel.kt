package com.example.authinterceptor.home

import androidx.lifecycle.viewModelScope
import com.example.authinterceptor.base.BaseViewModel
import com.example.domain.core.Result
import com.example.domain.usecase.GetLotteryInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserInfoUseCase: GetLotteryInfoUseCase
): BaseViewModel<HomeViewState, HomeEvent, HomeSideEffect>(HomeViewState()){
    override fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.UsernameInputChanged -> setState { copy(username = event.username) }
            is HomeEvent.PasswordInputChanged -> setState { copy(password = event.password) }
            is HomeEvent.GrantTypeInputChanged -> setState { copy(grant_type = event.grant_type) }
            is HomeEvent.LoginButtonClicked, HomeEvent.DoneButtonClicked -> {
                setState { copy(userInfoResult = Result.Loading()) }
                viewModelScope.launch {
                    val result = getUserInfoUseCase.invoke()
                    setState { copy(username = result.data!!.private_id) }

//                    if (result is Result.Success) {
//                        Log.d("HomerViewModel", "Result.Success")
//                        _sideEffects.trySend(HomeSideEffect.NavigateToLoginScreen)
//                    }
                }
            }
        }
    }
}