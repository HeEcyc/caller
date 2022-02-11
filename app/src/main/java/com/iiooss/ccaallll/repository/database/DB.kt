package com.iiooss.ccaallll.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iiooss.ccaallll.repository.database.dao.ThemeDao
import com.iiooss.ccaallll.repository.database.entity.ContactTheme
import com.iiooss.ccaallll.repository.database.entity.CustomTheme

@Database(entities = [CustomTheme::class, ContactTheme::class], version = 1)
abstract class DB : RoomDatabase() {

    abstract fun getThemeDao(): ThemeDao

}