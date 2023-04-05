package com.paxi.cst.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paxi.cst.repository.database.dao.ThemeDao
import com.paxi.cst.repository.database.entity.ContactTheme
import com.paxi.cst.repository.database.entity.CustomTheme

@Database(entities = [CustomTheme::class, ContactTheme::class], version = 1)
abstract class DB : RoomDatabase() {

    abstract fun getThemeDao(): ThemeDao

}