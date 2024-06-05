package com.wk.domain.usecase

import com.wk.domain.models.ui.AlarmModel
import com.wk.domain.repository.AlarmRepository
import javax.inject.Inject

class DeleteAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {

    suspend operator fun invoke(reminder: AlarmModel){
        alarmRepository.deleteAlarms(reminder)
    }
}