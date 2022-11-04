package com.example.authinterceptor.login

import com.example.domain.core.Result

sealed class LoginEvent {
    data class UsernameInputChanged(val username: String) : LoginEvent()
    data class PasswordInputChanged(val password: String) : LoginEvent()
    data class GrantTypeInputChanged(val grant_type: String) : LoginEvent()
    object DoneButtonClicked : LoginEvent()
    object LoginButtonClicked : LoginEvent()
}

sealed class LoginSideEffect {
    object NavigateToHomeScreen : LoginSideEffect()
}

data class LoginViewState(
    val username: String = "",
    val password: String = "",
    val grant_type: String = "",
    val loginResult: Result<Unit> = Result.Success(Unit),
)