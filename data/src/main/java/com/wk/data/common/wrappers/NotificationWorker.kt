package com.wk.data.common.wrappers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.Color.RED
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.media.AudioAttributes.USAGE_NOTIFICATION_RINGTONE
import android.media.RingtoneManager.TYPE_NOTIFICATION
import android.media.RingtoneManager.getDefaultUri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import androidx.hilt.work.HiltWorker
import androidx.work.ListenableWorker.Result.success
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted parameters: WorkerParameters)
    : Worker(context, parameters) {

    override fun doWork(): Result {
        val id = inputData.getLong(NOTIFICATION_ID, 0).toInt()
        sendNotification(id)

        return success()
    }

    private fun sendNotification(id: Int) {
        Log.d("CHECKCHECK", "sendNotification")

        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val titleNotification = "TEST"
        val subtitleNotification = "TEST"

//        val pendingIntent = if (SDK_INT >= S) {
//            getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE)
//        } else {
//            getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//        }

        val notification = NotificationCompat.Builder(context,
            NOTIFICATION_CHANNEL
        )
            .setSmallIcon(androidx.core.R.drawable.notification_bg)
            .setContentTitle(titleNotification)
            .setContentText(subtitleNotification)
//            .setDefaults(DEFAULT_ALL).setContentIntent(pendingIntent)
            .setAutoCancel(false)
            .setPriority(PRIORITY_MAX)

        if (SDK_INT >= O) {
            notification.setChannelId(NOTIFICATION_CHANNEL)

            val ringtoneManager = getDefaultUri(TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder().setUsage(USAGE_NOTIFICATION_RINGTONE)
                .setContentType(CONTENT_TYPE_SONIFICATION).build()

            val channel =
                NotificationChannel(
                    NOTIFICATION_CHANNEL,
                    NOTIFICATION_NAME, IMPORTANCE_HIGH)

            channel.enableLights(true)
            channel.lightColor = RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            channel.setSound(ringtoneManager, audioAttributes)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(id, notification.build())
    }

    companion object {
        const val NOTIFICATION_ID = "appName_notification_id"
        const val NOTIFICATION_NAME = "appName"
        const val NOTIFICATION_CHANNEL = "appName_channel_01"
        const val NOTIFICATION_WORK = "appName_notification_work"
    }
}