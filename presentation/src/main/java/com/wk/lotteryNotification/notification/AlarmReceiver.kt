package com.wk.lotteryNotification.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.wk.data.common.wrappers.MyNotificationManager
import com.wk.domain.models.ui.AlarmModel
import com.wk.domain.repository.AlarmRepository
import com.wk.lotteryNotification.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.Closeable
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver(), Closeable {

    @Inject
    lateinit var repository: AlarmRepository
    @Inject
    lateinit var notificationAlarmService: NotificationAlarmService
    @Inject
    lateinit var notificationManager: MyNotificationManager

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context?, intent: Intent?) {
        coroutineScope.launch {
            if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
                val alarms = repository.getAlarms()
                alarms.first().forEach {
                    notificationAlarmService.createAlarm(it.id!!.toLong(), it.reminderTimestamp)
                }
            } else {
                coroutineScope.launch {
                    val id = intent?.getLongExtra(ALARM_ID_KEY, -1)
                    val reminder = repository.getAlarm(id!!.toInt()).filterNotNull().first()
                    context?.let {
                        if (reminder.type == AlarmModel.LotteryType.MAIN) {
                            notificationManager.initNotificationParams(
                                CHANNEL_ID,
                                MAIN_CHANNEL_NAME,
                                reminder.id!!,
                                reminder.reminder,
                                reminder.reminder
                            )
                        } else {
                            notificationManager.initNotificationParams(
                                CHANNEL_ID,
                                PENSION_CHANNEL_NAME,
                                reminder.id!!,
                                reminder.reminder,
                                reminder.reminder
                            )
                        }

                        notificationManager.setIntent(MainActivity::class.java)
                        notificationManager.setNotificationBuilder()
                    }
                    repository.refreshAlarmFlow(reminder)
                    reminder.reminderTimestamp = reminder.reminderTimestamp + 3600000
                    repository.insertAlarms(reminder)
                }
            }
        }
    }

    override fun close() {
        coroutineScope.cancel()
    }

    companion object{
        const val CHANNEL_ID = "1241241"
        const val MAIN_CHANNEL_NAME = "MainLottery"
        const val PENSION_CHANNEL_NAME = "PensionLottery"
        const val ALARM_ID_KEY = "alarm_id"
    }
}