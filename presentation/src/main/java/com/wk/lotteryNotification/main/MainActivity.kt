package com.wk.lotteryNotification.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.wk.data.common.wrappers.MyNotificationManager
import com.wk.lotteryNotification.BaseApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

