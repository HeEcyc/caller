package com.callerafter.lovelycall

import android.app.Application
import com.callerafter.lovelycall.di.repositories
import com.callerafter.lovelycall.di.viewModels
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