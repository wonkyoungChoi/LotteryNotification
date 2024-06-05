package com.wk.domain.models.ui

data class AlarmModel (
    val reminder: String,
    val isCompleted: Boolean,
    val isExpired: Boolean,
    val type: LotteryType,
    val creationTimestamp: Long,
    var reminderTimestamp: Long,
    val id: Int? = null
) {
    enum class LotteryType {
        MAIN,
        PENSION
    }
}