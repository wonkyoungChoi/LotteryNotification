package com.wk.lotteryNotification.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wk.lotteryNotification.R
import com.wk.lotteryNotification.home.Type
import com.wk.lotteryNotification.ui.theme.RockBlue

@Composable
fun LotteryRoundButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.size_16)),
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colors.background,
            backgroundColor = RockBlue
        ),
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.size_16))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_down_left),
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(20.dp),
                    contentDescription = "drawable_icons",
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_down_right),
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(20.dp),
                    contentDescription = "drawable_icons",
                    tint = Color.Unspecified
                )
            }
            Text(
                modifier = Modifier.align(Alignment.Center),
                color = Color.Black,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                ),
                text = when (text) {
                    Type.MAIN.key -> {
                        stringResource(R.string.lottery)
                    }
                    Type.PENSION.key -> {
                        stringResource(R.string.lottery_pension)
                    }
                    else -> {
                        text
                    }
                }
            )
        }

    }
}