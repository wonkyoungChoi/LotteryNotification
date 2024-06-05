package com.wk.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wk.data.data_storage.database.DBInfo
import com.wk.domain.models.ui.AlarmModel

@Entity(tableName = DBInfo.REMINDER_TABLE_NAME)
data class AlarmEntity(
    @ColumnInfo(DBInfo.AlarmTable.ALARM_COLUMN)
    val reminder: String,
    @ColumnInfo(DBInfo.AlarmTable.COMPLETED_COLUMN)
    val isCompleted: Boolean,
    @ColumnInfo(DBInfo.AlarmTable.LOTTERY_TYPE_COLUMN)
    val type: AlarmModel.LotteryType,
    @ColumnInfo(DBInfo.AlarmTable.CREATION_TIME_COLUMN)
    val creationTimestamp: Long,
    @ColumnInfo(DBInfo.AlarmTable.REMINDER_TIME_COLUMN)
    val reminderTimestamp: Long,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(DBInfo.AlarmTable.ID_COLUMN)
    val id: Int? = null
)