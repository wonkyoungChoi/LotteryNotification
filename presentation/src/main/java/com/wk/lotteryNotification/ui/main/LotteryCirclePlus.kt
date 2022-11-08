package com.wk.lotteryNotification.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.wk.lotteryNotification.R

@Composable
fun LotteryCirclePlus() {
    val painter = painterResource(id = R.drawable.ic_plus
    )
    val description = "PLUS"
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(
                color = MaterialTheme.colors.background,
                shape = CircleShape
            )
            .width(dimensionResource(id = R.dimen.size_40))
            .height(dimensionResource(id = R.dimen.size_40))
    ) {
        Image(painter = painter,
            contentDescription = description)
    }
}