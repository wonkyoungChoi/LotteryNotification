package com.example.authinterceptor.login

import android.util.Log
import com.example.authinterceptor.base.BaseViewModel
import com.example.domain.models.requests.LotteryInfoRequest
import com.example.domain.core.Result
import com.example.domain.usecase.LoginUseCase
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): BaseViewModel<LoginViewState, LoginEvent, LoginSideEffect>(LoginViewState()){
    override fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.UsernameInputChanged -> setState { copy(username = event.username) }
            is LoginEvent.PasswordInputChanged -> setState { copy(password = event.password) }
            is LoginEvent.GrantTypeInputChanged -> setState { copy(grant_type = event.grant_type) }
            is LoginEvent.LoginButtonClicked, LoginEvent.DoneButtonClicked -> {
                setState { copy(loginResult = Result.Loading()) }
                viewModelScope.launch {
                    val result = loginUseCase.invoke(
                        LotteryInfoRequest(
                            username = getState().username,
                            password = getState().password,
                            grant_type = getState().grant_type
                        )
                    )
                    setState { copy(loginResult = result) }
                    if (result is Result.Success) {
                        Log.d("LoginViewModel", "Result.Success")
                        _sideEffects.trySend(LoginSideEffect.NavigateToHomeScreen)
                    }
                }
            }
        }
    }

}