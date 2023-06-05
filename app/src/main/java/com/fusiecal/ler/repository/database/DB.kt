package com.fusiecal.ler.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fusiecal.ler.repository.database.dao.ThemeDao
import com.fusiecal.ler.repository.database.entity.ContactTheme
import com.fusiecal.ler.repository.database.entity.CustomTheme

@Database(entities = [CustomTheme::class, ContactTheme::class], version = 1)
abstract class DB : RoomDatabase() {

    abstract fun getThemeDao(): ThemeDao

}