package com.wk.lotteryNotification.ui.main

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.wk.presentation.R

@Composable
fun LotteryTableRow(rank: String, takeMoney: String, winner: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = rank,
            modifier = Modifier
                .border(width = dimensionResource(id = R.dimen.size_1),
                    color = MaterialTheme.colors.onSurface)
                .weight(0.1f)
                .padding(dimensionResource(id = R.dimen.size_2)),
            textAlign = TextAlign.Start
        )
        Text(text = takeMoney,
            modifier = Modifier
                .border(width = dimensionResource(id = R.dimen.size_1),
                    color = MaterialTheme.colors.onSurface)
                .weight(0.2f)
                .padding(dimensionResource(id = R.dimen.size_2)),
            textAlign = TextAlign.Start
        )
        Text(text = winner,
            modifier = Modifier
                .border(width = dimensionResource(id = R.dimen.size_1),
                        color = MaterialTheme.colors.onSurface)
                .weight(0.2f)
                .padding(dimensionResource(id = R.dimen.size_2)),
            textAlign = TextAlign.Start
        )
    }
}