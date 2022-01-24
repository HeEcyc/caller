package com.callerafter.lovelycall.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.callerafter.lovelycall.repository.database.dao.ThemeDao
import com.callerafter.lovelycall.repository.database.entity.ContactTheme
import com.callerafter.lovelycall.repository.database.entity.CustomTheme

@Database(entities = [CustomTheme::class, ContactTheme::class], version = 1)
abstract class DB : RoomDatabase() {

    abstract fun getThemeDao(): ThemeDao

}