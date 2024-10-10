package com.wk.lotteryNotification.ui.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        modifier = modifier
            .height(dimensionResource(id = R.dimen.size_64))
            .width(dimensionResource(id = R.dimen.size_64))
            .padding(end = dimensionResource(id = R.dimen.size_10)),
        border = BorderStroke(1.dp, Color.Black),
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colors.background,
            backgroundColor = Color.White
        ),
        contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.size_8))
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(horizontal = dimensionResource(R.dimen.size_8))
        ){
            Image(painter = painter,
                contentDescription = description,
                modifier = Modifier.wrapContentSize())
            Text(
                text = "QR 확인",
                color = Color.Black,
                fontSize = 11.sp
            )
        }
    }
}