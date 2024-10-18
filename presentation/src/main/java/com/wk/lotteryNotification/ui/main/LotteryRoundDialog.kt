package com.wk.lotteryNotification.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import com.wk.lotteryNotification.home.HomeViewState
import com.wk.presentation.R

@Composable
fun InputSelectRoundDialogView(
    round: String,
    viewState: HomeViewState,
    onClick: (String) -> Unit) {

    val items = arrayListOf<String>()

    if(round.isDigitsOnly()) {
        items.clear()
        for (i in round.toInt() downTo 1) {
            items.add(i.toString() + "회")
        }
        MaterialTheme {
            if (viewState.roundSelected.value) {
                Dialog(
                    onDismissRequest = { viewState.roundSelected.value = false },
                    content = {
                        Column(Modifier.background(Color.White)) {

                            Text(
                                modifier = Modifier.padding(
                                    start = dimensionResource(id = R.dimen.size_10),
                                    end = dimensionResource(id = R.dimen.size_10),
                                    top = dimensionResource(id = R.dimen.size_10),
                                    bottom = dimensionResource(id = R.dimen.size_10)
                                ),
                                text = stringResource(id = R.string.lottery_round),
                                fontWeight = FontWeight(700),
                            )
                            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.size_10)))
                            LazyColumn {
                                itemsIndexed(items) { position, item ->
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable(
                                                enabled = true,
                                                interactionSource = remember { MutableInteractionSource() },
                                                indication = ripple(bounded = true),
                                                onClick = {
                                                    onClick((items.size - position).toString())
                                                }
                                            )
                                            .padding(
                                                start = dimensionResource(id = R.dimen.size_10),
                                                end = dimensionResource(id = R.dimen.size_10),
                                                top = dimensionResource (id = R.dimen.size_10),
                                                bottom = dimensionResource(id = R.dimen.size_10)
                                            ),
                                        text = (item),
                                        color = colorResource(id = R.color.black),
                                    )
                                }
                            }
                        }
                    }
                )}
        }
    } else {
        //TODO ERROR 처리

    }
}
