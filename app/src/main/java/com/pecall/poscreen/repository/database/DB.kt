package com.pecall.poscreen.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pecall.poscreen.repository.database.dao.ThemeDao
import com.pecall.poscreen.repository.database.entity.ContactTheme
import com.pecall.poscreen.repository.database.entity.CustomTheme

@Database(entities = [CustomTheme::class, ContactTheme::class], version = 1)
abstract class DB : RoomDatabase() {

    abstract fun getThemeDao(): ThemeDao

}