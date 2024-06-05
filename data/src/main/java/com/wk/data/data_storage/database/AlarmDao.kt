package com.wk.data.data_storage.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wk.data.model.AlarmEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Query("SELECT * FROM ${DBInfo.REMINDER_TABLE_NAME} WHERE ${DBInfo.AlarmTable.ID_COLUMN} =:id")
    fun getReminderById(id: Int): Flow<AlarmEntity>

    @Query("SELECT * FROM ${DBInfo.REMINDER_TABLE_NAME}")
    fun getAllReminders(): Flow<List<AlarmEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: AlarmEntity): Long

    @Delete
    suspend fun deleteReminder(reminder: AlarmEntity)
}