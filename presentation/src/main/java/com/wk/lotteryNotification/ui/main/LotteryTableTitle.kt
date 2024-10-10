package com.wk.lotteryNotification.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.wk.lotteryNotification.R

@Composable
fun LotteryTableTitle(head1: String, head2: String, head3: String) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxWidth()
    ) {
        Text(text = head1,
            color = Color.White,
            modifier = Modifier
                .border(
                    width = dimensionResource(id = R.dimen.size_1),
                    color = MaterialTheme.colors.onSurface
                )
                .padding(dimensionResource(id = R.dimen.size_4))
                .weight(0.1f),
            textAlign = TextAlign.Center)
        Text(text = head2,
            color = Color.White,
            modifier = Modifier
                .border(
                    width = dimensionResource(id = R.dimen.size_1),
                    color = MaterialTheme.colors.onSurface
                )
                .padding(dimensionResource(id = R.dimen.size_4))
                .weight(0.2f),
            textAlign = TextAlign.Center)
        Text(text = head3,
            color = Color.White,
            modifier = Modifier
                .border(
                    width = dimensionResource(id = R.dimen.size_1),
                    color = MaterialTheme.colors.onSurface
                )
                .padding(dimensionResource(id = R.dimen.size_4))
                .weight(0.2f),
            textAlign = TextAlign.Center)
    }
}