package com.wk.domain.repository

import android.content.BroadcastReceiver
import com.wk.domain.models.ui.AlarmModel
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    fun getAlarms(): Flow<List<AlarmModel>>

    fun getAlarm(id: Int): Flow<AlarmModel?>

    suspend fun deleteAlarms(reminder: AlarmModel)

    suspend fun insertAlarms(reminder: AlarmModel): Long

    fun checkIsFirstLaunch(): Boolean

    suspend fun refreshAlarmFlow(reminder: AlarmModel)
}