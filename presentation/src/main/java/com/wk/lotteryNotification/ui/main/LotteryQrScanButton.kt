package com.wk.lotteryNotification.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wk.lotteryNotification.R

@Composable
fun LotteryQrScanButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val painter = painterResource(id = R.drawable.ic_qr_code_scan)
    val description = "QR_SCAN"
    Button(
//        elevation = elevation(
//            defaultElevation = 0.dp,
//            pressedElevation = 0.dp
//        ),
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colors.background,
            backgroundColor = Color.White
        ),
        contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.size_16))
    ) {
        Image(painter = painter,
            contentDescription = description)
    }
}