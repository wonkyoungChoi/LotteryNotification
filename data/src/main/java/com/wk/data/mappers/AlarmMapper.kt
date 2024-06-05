package com.wk.data.mappers

import com.wk.data.model.AlarmEntity
import com.wk.domain.models.ui.AlarmModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlarmMapper @Inject constructor() {

    private fun mapReminderEntityToDomain(alarmModel: AlarmEntity): AlarmModel {
        return AlarmModel(
            alarmModel.reminder,
            alarmModel.isCompleted,
            alarmModel.reminderTimestamp <= System.currentTimeMillis(),
            alarmModel.type,
            alarmModel.creationTimestamp,
            alarmModel.reminderTimestamp,
            alarmModel.id
        )
    }

    fun mapReminderDomainToData(alarmModel: AlarmModel): AlarmEntity {
        return AlarmEntity(
            alarmModel.reminder,
            alarmModel.isCompleted,
            alarmModel.type,
            alarmModel.creationTimestamp,
            alarmModel.reminderTimestamp,
            alarmModel.id
        )
    }

    fun mapReminderEntityFlowToDomain(reminderFlow: Flow<AlarmEntity?>): Flow<AlarmModel?> {
        return reminderFlow.map {
            it?.let {
                mapReminderEntityToDomain(it)
            }
        }
    }

    fun mapReminderEntityListFlowToDomain(
        reminderFlow: Flow<List<AlarmEntity>>
    ): Flow<List<AlarmModel>> {

        return reminderFlow.map {
            it.map {
                mapReminderEntityToDomain(it)
            }
        }
    }
}