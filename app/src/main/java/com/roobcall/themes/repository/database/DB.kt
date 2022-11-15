package com.roobcall.themes.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.roobcall.themes.repository.database.dao.ThemeDao
import com.roobcall.themes.repository.database.entity.ContactTheme
import com.roobcall.themes.repository.database.entity.CustomTheme

@Database(entities = [CustomTheme::class, ContactTheme::class], version = 1)
abstract class DB : RoomDatabase() {

    abstract fun getThemeDao(): ThemeDao

}