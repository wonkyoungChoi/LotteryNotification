package com.wk.domain.usecase

import com.wk.domain.repository.AlarmRepository
import javax.inject.Inject

class CheckIsFirstLaunchUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {

    operator fun invoke(): Boolean{
        return alarmRepository.checkIsFirstLaunch()
    }
}