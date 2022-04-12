package com.maxios.maxcall.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maxios.maxcall.repository.database.dao.ThemeDao
import com.maxios.maxcall.repository.database.entity.ContactTheme
import com.maxios.maxcall.repository.database.entity.CustomTheme

@Database(entities = [CustomTheme::class, ContactTheme::class], version = 1)
abstract class DB : RoomDatabase() {

    abstract fun getThemeDao(): ThemeDao

}