package com.wk.lotteryNotification.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.wk.lotteryNotification.base.BaseApp
import com.wk.presentation.BuildConfig
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}

        if (BuildConfig.DEBUG) {
            MobileAds.setRequestConfiguration(
                RequestConfiguration.Builder().setTestDeviceIds(listOf("ABCDEF012345")).build()
            )
        }

        setContent {
            BaseApp()
        }
    }

    override fun onStart() {
        super.onStart()


    }

    override fun onResume() {
        super.onResume()
    }
}

