package com.galaxy.call.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.galaxy.call.repository.database.dao.ThemeDao
import com.galaxy.call.repository.database.entity.ContactTheme
import com.galaxy.call.repository.database.entity.CustomTheme

@Database(entities = [CustomTheme::class, ContactTheme::class], version = 1)
abstract class DB : RoomDatabase() {

    abstract fun getThemeDao(): ThemeDao

}