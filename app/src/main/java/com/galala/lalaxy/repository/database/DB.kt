package com.galala.lalaxy.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.galala.lalaxy.repository.database.dao.ThemeDao
import com.galala.lalaxy.repository.database.entity.ContactTheme
import com.galala.lalaxy.repository.database.entity.CustomTheme

@Database(entities = [CustomTheme::class, ContactTheme::class], version = 1)
abstract class DB : RoomDatabase() {

    abstract fun getThemeDao(): ThemeDao

}