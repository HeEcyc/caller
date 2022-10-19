package com.glasserino.caller.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.glasserino.caller.repository.database.dao.ThemeDao
import com.glasserino.caller.repository.database.entity.ContactTheme
import com.glasserino.caller.repository.database.entity.CustomTheme

@Database(entities = [CustomTheme::class, ContactTheme::class], version = 1)
abstract class DB : RoomDatabase() {

    abstract fun getThemeDao(): ThemeDao

}