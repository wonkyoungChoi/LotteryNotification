package com.wk.domain.usecase

import com.wk.domain.models.ui.AlarmModel
import com.wk.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlarmByIdUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {

    operator fun invoke(id: Int): Flow<AlarmModel?> {
        return alarmRepository.getAlarm(id)
    }
}