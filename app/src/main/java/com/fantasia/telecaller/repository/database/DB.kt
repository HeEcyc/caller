package com.fantasia.telecaller.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fantasia.telecaller.repository.database.dao.ThemeDao
import com.fantasia.telecaller.repository.database.entity.ContactTheme
import com.fantasia.telecaller.repository.database.entity.CustomTheme

@Database(entities = [CustomTheme::class, ContactTheme::class], version = 1)
abstract class DB : RoomDatabase() {

    abstract fun getThemeDao(): ThemeDao

}