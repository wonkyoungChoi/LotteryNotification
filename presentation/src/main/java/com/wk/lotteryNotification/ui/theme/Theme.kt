package com.wk.lotteryNotification.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.medium.client.presentation.ui.theme.Black
import com.medium.client.presentation.ui.theme.ChathamsBlue
import com.medium.client.presentation.ui.theme.Gray
import com.medium.client.presentation.ui.theme.LightGray
import com.medium.client.presentation.ui.theme.Orange
import com.medium.client.presentation.ui.theme.Red
import com.medium.client.presentation.ui.theme.RockBlue
import com.medium.client.presentation.ui.theme.Shapes
import com.medium.client.presentation.ui.theme.Solitude
import com.medium.client.presentation.ui.theme.Typography
import com.medium.client.presentation.ui.theme.White

private val DarkColorPalette = darkColors(
    primary = ChathamsBlue,
    primaryVariant = RockBlue,
    onPrimary = White,
    secondary = Solitude,
    secondaryVariant = Gray,
    surface = LightGray,
    background = White,
    onBackground = Black,
    error = Red,
    onError = Orange
)

private val LightColorPalette = lightColors(
    primary = ChathamsBlue,
    primaryVariant = RockBlue,
    onPrimary = White,
    secondary = Solitude,
    secondaryVariant = Gray,
    surface = LightGray,
    background = White,
    onBackground = Black,
    error = Red,
    onError = Orange

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
//    val colors = if (darkTheme) {
//        DarkColorPalette
//    } else {
//        LightColorPalette
//    }
    val colors = LightColorPalette
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(colors.primary)

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}