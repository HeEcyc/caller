package com.delice.cscreen

import android.app.Application
import com.delice.cscreen.di.repositories
import com.delice.cscreen.di.viewModels
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin { modules(repositories, viewModels) }
    }

}