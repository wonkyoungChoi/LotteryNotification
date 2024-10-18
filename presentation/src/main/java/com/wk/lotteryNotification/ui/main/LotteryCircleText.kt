package com.wk.lotteryNotification.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.text.isDigitsOnly
import com.wk.lotteryNotification.ui.theme.ChathamsBlue
import com.wk.lotteryNotification.ui.theme.DarkGray
import com.wk.lotteryNotification.ui.theme.DarkRed
import com.wk.lotteryNotification.ui.theme.Green
import com.wk.lotteryNotification.ui.theme.Yellow
import com.wk.presentation.R

@Composable
fun LotteryCircleText(
    text: String,
) {
    Box(
        modifier = Modifier
            .size(dimensionResource(R.dimen.size_40))
            .clip(CircleShape)
            .background(color = if(text.isDigitsOnly()) {
                val num = text.toInt()
                when  {
                    num < 10 -> Yellow
                    num < 20 -> ChathamsBlue
                    num < 30 -> DarkRed
                    num < 40 -> DarkGray
                    else -> Green
                }
            } else {
                MaterialTheme.colors.secondary
            }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold
        )
    }
}