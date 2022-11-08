package com.wk.lotteryNotification.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.core.text.isDigitsOnly
import com.wk.lotteryNotification.R

@Composable
fun LotteryCircleText(
    text: String
) {
    Box(
        modifier = Modifier
            .size(dimensionResource(R.dimen.size_40))
            .clip(CircleShape)
            .background(color = if(text.isDigitsOnly()) {
                if(text.toInt() > 0) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.secondary
                }
            } else {
                MaterialTheme.colors.secondary
            }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = if(text.isDigitsOnly()) text else "")
    }
}