package com.wk.data.data_storage.database

object DBInfo {

    const val NOTE_TABLE_NAME = "notes"
    const val REMINDER_TABLE_NAME = "reminders"

    object AlarmTable{
        const val ID_COLUMN = "id"
        const val ALARM_COLUMN = "alarm"
        const val COMPLETED_COLUMN = "completed"
        const val LOTTERY_TYPE_COLUMN = "lottery_type_column"
        const val CREATION_TIME_COLUMN = "creation_time"
        const val REMINDER_TIME_COLUMN = "reminder_time"
    }
}