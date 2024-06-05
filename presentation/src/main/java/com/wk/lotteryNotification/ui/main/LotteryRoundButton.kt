package com.wk.lotteryNotification.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.wk.lotteryNotification.R
import com.wk.lotteryNotification.home.Type

@Composable
fun LotteryRoundButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
            .padding(dimensionResource(R.dimen.size_16)),
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colors.background,
            backgroundColor = MaterialTheme.colors.primary
        ),
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.size_16))
    ) {
        Text(text = when (text) {
            Type.MAIN.key -> {
                stringResource(R.string.lottery)
            }
            Type.PENSION.key -> {
                stringResource(R.string.lottery_pension)
            }
            else -> {
                text
            }
        })
    }
}