package com.callerafter.lovelycall.di

import androidx.room.Room
import com.callerafter.lovelycall.App
import com.callerafter.lovelycall.repository.ContactsRepository
import com.callerafter.lovelycall.repository.LocaleRepository
import com.callerafter.lovelycall.repository.PreferencesRepository
import com.callerafter.lovelycall.repository.ThemeRepository
import com.callerafter.lovelycall.repository.database.DB
import org.koin.dsl.module

val repositories = module {
    single { PreferencesRepository(App.instance) }
    single {
        ThemeRepository(
            Room.databaseBuilder(App.instance, DB::class.java, "themesDB").build().getThemeDao()
        )
    }
    single { ContactsRepository(App.instance) }
    single { LocaleRepository(get()) }
}