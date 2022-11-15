package com.roobcall.themes

import android.app.Application
import com.roobcall.themes.di.repositories
import com.roobcall.themes.di.viewModels
import org.koin.core.context.startKoin

class App : Application() {
//test
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin { modules(repositories, viewModels) }
    }

}