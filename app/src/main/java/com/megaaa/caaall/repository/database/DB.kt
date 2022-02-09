package com.megaaa.caaall.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.megaaa.caaall.repository.database.dao.ThemeDao
import com.megaaa.caaall.repository.database.entity.ContactTheme
import com.megaaa.caaall.repository.database.entity.CustomTheme

@Database(entities = [CustomTheme::class, ContactTheme::class], version = 1)
abstract class DB : RoomDatabase() {

    abstract fun getThemeDao(): ThemeDao

}