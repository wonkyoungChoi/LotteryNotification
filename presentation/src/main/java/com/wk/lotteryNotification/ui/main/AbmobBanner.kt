package com.wk.lotteryNotification.ui.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.wk.presentation.BuildConfig

@Composable
fun AdmobBanner(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            // on below line specifying ad view.
            AdView(context).apply {
                // on below line specifying ad size
                //adSize = AdSize.BANNER
                // on below line specifying ad unit id
                // currently added a test ad unit id.
                setAdSize(AdSize.BANNER)
                adUnitId = if (BuildConfig.DEBUG) {
                    "ca-app-pub-3940256099942544/6300978111"
                } else {
                    "ca-app-pub-4384467466978197/9004074004"
                }
                // calling load ad to load our ad.
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}