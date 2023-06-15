package com.docall.jocall.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.docall.jocall.repository.database.dao.ThemeDao
import com.docall.jocall.repository.database.entity.ContactTheme
import com.docall.jocall.repository.database.entity.CustomTheme

@Database(entities = [CustomTheme::class, ContactTheme::class], version = 1)
abstract class DB : RoomDatabase() {

    abstract fun getThemeDao(): ThemeDao

}