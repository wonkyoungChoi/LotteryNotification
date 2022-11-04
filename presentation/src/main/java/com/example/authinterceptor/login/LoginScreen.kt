package com.example.authinterceptor.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.authinterceptor.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalComposeUiApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val viewState = loginViewModel.uiState.collectAsState().value
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(loginViewModel.sideEffects) {
        loginViewModel.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is LoginSideEffect.NavigateToHomeScreen -> navigator.navigate(HomeScreenDestination)
            }
        }
    }

//    AppInputField(
//        value = viewState.username,
//        onValueChange = { loginViewModel.onEvent(LoginEvent.UsernameInputChanged(it)) },
//        placeholderText = stringResource(R.string.username),
//        trailingIcon = painterResource(R.drawable.ic_clear_button),
//        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
//    )

}