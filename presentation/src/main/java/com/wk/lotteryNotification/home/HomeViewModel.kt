package com.wk.lotteryNotification.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.wk.domain.core.Result
import com.wk.domain.models.ui.AlarmModel
import com.wk.domain.usecase.AddAlarmUseCase
import com.wk.domain.usecase.GetLotteryInfoUseCase
import com.wk.domain.usecase.GetLotterySearchInfoUseCase
import com.wk.domain.usecase.GetPensionLotteryInfoUseCase
import com.wk.domain.usecase.GetPensionLotterySearchInfoUseCase
import com.wk.lotteryNotification.base.BaseViewModel
import com.wk.lotteryNotification.notification.NotificationAlarmService
import com.wk.lotteryNotification.util.Constants.MAIN_TYPE
import com.wk.lotteryNotification.util.Constants.PENSION_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLotteryInfoUseCase: GetLotteryInfoUseCase,
    private val getLotterySearchInfoUseCase: GetLotterySearchInfoUseCase,
    private val getPensionLotteryInfoUseCase: GetPensionLotteryInfoUseCase,
    private val getPensionLotterySearchInfoUseCase: GetPensionLotterySearchInfoUseCase,
    private val addAlarmUseCase: AddAlarmUseCase,
    private val notificationAlarmService: NotificationAlarmService
): BaseViewModel<HomeViewState, HomeEvent, HomeSideEffect>(HomeViewState()){

    init {
        setState { copy(dataState = Result.Loading())}
        viewModelScope.launch {
            val result = getLotteryInfoUseCase.invoke(MAIN_TYPE)

            if(result.data != null) {
                setState {
                    copy(
                        dataState = result,
                        totalRound = result.data!!.lotteryRound,
                        lotteryInfo = result.data
                    )
                }
            }
        }
    }

    override fun onEvent(event: HomeEvent) {

        when(event) {
            is HomeEvent.SelectTypeButtonClicked -> {
                setState { copy(typeSelected = mutableStateOf(true)) }
            }
            is HomeEvent.SelectRoundButtonClicked -> {
                setState { copy(roundSelected = mutableStateOf(true)) }
            }
            is HomeEvent.SelectQrScanButtonClicked -> {
                _sideEffects.trySend(HomeSideEffect.NavigateToQrCheckScreen)
            }
            is HomeEvent.SelectGoToSetting -> {
                _sideEffects.trySend(HomeSideEffect.GoToSetting)
            }
            is HomeEvent.TypeButtonTextChanged -> {
                setState { copy(dataState = Result.Loading())}
                viewModelScope.launch {
                    when(event.type) {
                        Type.MAIN.key -> {
                            val result = getLotteryInfoUseCase.invoke(MAIN_TYPE)
                            if(result.data != null) {
                                setState {
                                    copy(
                                        dataState = result,
                                        type = event.type,
                                        totalRound = result.data!!.lotteryRound,
                                        lotteryInfo = result.data
                                    )
                                }
                            }
                        }
                        Type.PENSION.key -> {
                            val result = getPensionLotteryInfoUseCase.invoke(PENSION_TYPE)
                            if(result.data != null) {
                                setState {
                                    copy(
                                        dataState = result,
                                        type = event.type,
                                        totalRound = result.data!!.lotteryRound,
                                        lotteryInfo = result.data
                                    )
                                }
                            }
                        }
                    }

                }
                setState { copy(typeSelected = mutableStateOf(false)) }

            }
            is HomeEvent.RoundButtonTextChanged -> {
                setState { copy(dataState = Result.Loading())}
                viewModelScope.launch {
                    when(event.type) {
                        Type.MAIN.key -> {
                            val result = getLotterySearchInfoUseCase.invoke(MAIN_TYPE, event.round)

                            if(result.data != null) {
                                setState {
                                    copy(
                                        dataState = result,
                                        lotteryInfo = result.data
                                    )
                                }
                            }
                        }
                        Type.PENSION.key -> {
                            val result = getPensionLotterySearchInfoUseCase.invoke(PENSION_TYPE, event.round)

                            if(result.data != null) {
                                setState {
                                    copy(
                                        dataState = result,
                                        lotteryInfo = result.data
                                    )
                                }
                            }
                        }
                    }

                }
                setState { copy(roundSelected = mutableStateOf(false)) }
            }
            is HomeEvent.StartApp -> {
                val mainWinningTime = getMainWinningTime()
                val pensionWinningTime = getPensionWinningTime()
                val dateFormat = SimpleDateFormat("yyyy/MM/dd EEEE HH:mm:ss z", Locale.KOREAN)
                val mainAlarm = AlarmModel(
                    id = 1,
                    reminder = "로또 당첨번호 조회하기 ${dateFormat.format(mainWinningTime)}",
                    isCompleted = false,
                    isExpired = System.currentTimeMillis() > mainWinningTime,
                    type = AlarmModel.LotteryType.MAIN,
                    creationTimestamp = System.currentTimeMillis(),
                    reminderTimestamp = mainWinningTime
                )

                val pensionAlarm = AlarmModel(
                    id = 2,
                    reminder = "연금복권 당첨번호 조회하기 ${dateFormat.format(pensionWinningTime)}",
                    isCompleted = false,
                    isExpired = System.currentTimeMillis() > pensionWinningTime,
                    type = AlarmModel.LotteryType.PENSION,
                    creationTimestamp = System.currentTimeMillis(),
                    reminderTimestamp = pensionWinningTime
                )
                viewModelScope.launch {
                    if (mainAlarm.reminderTimestamp > pensionAlarm.reminderTimestamp) {
                        val mainId = addAlarmUseCase.invoke(mainAlarm)
                        setNotificationAlarm(mainId, mainAlarm)

                        val pensionId = addAlarmUseCase.invoke(pensionAlarm)
                        setNotificationAlarm(pensionId, pensionAlarm)

                    } else {
                        val pensionId = addAlarmUseCase.invoke(pensionAlarm)
                        setNotificationAlarm(pensionId, pensionAlarm)

                        val mainId = addAlarmUseCase.invoke(mainAlarm)
                        setNotificationAlarm(mainId, mainAlarm)
                    }
                }
            }
            else -> {}
        }
    }

    private fun getMainWinningTime(): Long {
        val calendar = Calendar.getInstance(Locale.KOREA)
        calendar.time = Date()
        // 토요일 21시
        calendar.add(Calendar.DAY_OF_MONTH, (7-calendar.get(Calendar.DAY_OF_WEEK)))
        calendar.set(Calendar.HOUR_OF_DAY, 21)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val mainWinningDate = if (calendar.time.time < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 7)
            calendar.time
        } else {
            calendar.time
        }

        return mainWinningDate.time
    }

    private fun getPensionWinningTime(): Long {
        val calendar = Calendar.getInstance(Locale.KOREA)
        calendar.time = Date()
        // 목요일 19시 30분
        calendar.add(Calendar.DAY_OF_MONTH, (5-calendar.get(Calendar.DAY_OF_WEEK)))
        calendar.set(Calendar.HOUR_OF_DAY, 19)
        calendar.set(Calendar.MINUTE, 30)
        calendar.set(Calendar.SECOND, 0)

        val pensionWinningDate = if (calendar.time.time < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 7)
            calendar.time
        } else {
            calendar.time
        }

        return pensionWinningDate.time
    }

    private fun setNotificationAlarm(id: Long, alarm: AlarmModel) {
//        notificationAlarmService.clearAlarm(id)
//        if (!alarm.isCompleted) {
//            notificationAlarmService.createAlarm(id, alarm.reminderTimestamp)
//        }
    }
}