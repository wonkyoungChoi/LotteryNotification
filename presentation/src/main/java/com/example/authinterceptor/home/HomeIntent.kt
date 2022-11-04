package com.example.authinterceptor.home

import com.example.domain.core.Result

sealed class HomeEvent {
    data class UsernameInputChanged(val username: String) : HomeEvent()
    data class PasswordInputChanged(val password: String) : HomeEvent()
    data class GrantTypeInputChanged(val grant_type: String) : HomeEvent()
    object DoneButtonClicked : HomeEvent()
    object LoginButtonClicked : HomeEvent()
}

sealed class HomeSideEffect {
    object NavigateToLoginScreen : HomeSideEffect()
}

data class HomeViewState(
    val username: String = "",
    val password: String = "",
    val grant_type: String = "",
    val userInfoResult: Result<Unit> = Result.Success(Unit),
)