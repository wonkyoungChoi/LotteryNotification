package com.wk.data.common.wrappers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface MyNotificationManager {
    fun initNotificationParams(
        channelID: String,
        channelName: String,
        notificationID: Int,
        contentText: String? = null,
        title: String? = null
    )

    fun setIntent(
        directTo: Class<*>
    )

    fun setNotificationBuilder()
}

class MyNotificationManagerImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val notificationManager: NotificationManager
) : MyNotificationManager {

    private lateinit var channelID: String
    private lateinit var channelName: String
    private var notificationID: Int = -1
    private var contentText: String? = null
    private lateinit var directTo: Class<*>
    private var icon: Int? = null
    private var title: String? = null


    private val contentIntent by lazy {
        PendingIntent.getActivity(
            context,
            0,
            Intent(context, directTo),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun initNotificationParams(
        channelID: String,
        channelName: String,
        notificationID: Int,
        title: String?,
        contentText: String?,
        ) {
        this.channelID = channelID
        this.channelName = channelName
        this.notificationID = notificationID
        this.title = title
        this.contentText = contentText
    }

    override fun setNotificationBuilder() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(createChannel())
        }

        val notificationBuilder = NotificationCompat.Builder(context, channelID)
                .setContentTitle(channelName)// fun
                .setContentIntent(contentIntent)
                .setContentText(contentText ?: "")
                .setSmallIcon(android.R.drawable.btn_star_big_on)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(icon ?: android.R.drawable.ic_menu_view)
                .setAutoCancel(true)

        notificationManager.notify(
            notificationID,
            notificationBuilder.build()
        )
    }

    override fun setIntent(directTo: Class<*>) {
        this.directTo = directTo
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() =
        NotificationChannel(
            channelID,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
}
