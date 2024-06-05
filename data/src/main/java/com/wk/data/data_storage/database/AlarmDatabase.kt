package com.wk.data.data_storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wk.data.model.AlarmEntity

@Database(entities = [AlarmEntity::class], version = 2)
abstract class AlarmDatabase: RoomDatabase() {

    abstract fun alarmDao(): AlarmDao

}