package com.wk.data.datasource

import com.wk.data.data_storage.FirstTimeLaunchAppStorage
import com.wk.data.data_storage.database.AlarmDao
import com.wk.data.mappers.AlarmMapper
import com.wk.domain.models.ui.AlarmModel
import com.wk.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val reminderDao: AlarmDao,
    private val mapper: AlarmMapper,
    private val firstTimeLaunchAppStorage: FirstTimeLaunchAppStorage
): AlarmRepository {

    override fun getAlarms(): Flow<List<AlarmModel>> {
        val entityReminders = reminderDao.getAllReminders()
        return mapper.mapReminderEntityListFlowToDomain(entityReminders)
    }

    override fun getAlarm(id: Int): Flow<AlarmModel?> {
        val entityReminder = reminderDao.getReminderById(id)
        return mapper.mapReminderEntityFlowToDomain(entityReminder)
    }

    override suspend fun deleteAlarms(reminder: AlarmModel) {
        val entityReminder = mapper.mapReminderDomainToData(reminder)
        reminderDao.deleteReminder(entityReminder)
    }

    override suspend fun insertAlarms(reminder: AlarmModel): Long {
        val entityReminder = mapper.mapReminderDomainToData(reminder)
        return reminderDao.insertReminder(entityReminder)
    }

    override suspend fun refreshAlarmFlow(reminder: AlarmModel){
        val entityReminder = mapper.mapReminderDomainToData(reminder)
        reminderDao.insertReminder(entityReminder)
    }

    override fun checkIsFirstLaunch(): Boolean{
        return firstTimeLaunchAppStorage.checkIsFirstLaunch()
    }
}