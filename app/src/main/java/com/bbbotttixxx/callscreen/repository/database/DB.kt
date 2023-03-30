package com.bbbotttixxx.callscreen.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bbbotttixxx.callscreen.repository.database.dao.ThemeDao
import com.bbbotttixxx.callscreen.repository.database.entity.ContactTheme
import com.bbbotttixxx.callscreen.repository.database.entity.CustomTheme

@Database(entities = [CustomTheme::class, ContactTheme::class], version = 1)
abstract class DB : RoomDatabase() {

    abstract fun getThemeDao(): ThemeDao

}