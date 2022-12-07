package com.delice.cscreen.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.delice.cscreen.repository.database.dao.ThemeDao
import com.delice.cscreen.repository.database.entity.ContactTheme
import com.delice.cscreen.repository.database.entity.CustomTheme

@Database(entities = [CustomTheme::class, ContactTheme::class], version = 1)
abstract class DB : RoomDatabase() {

    abstract fun getThemeDao(): ThemeDao

}