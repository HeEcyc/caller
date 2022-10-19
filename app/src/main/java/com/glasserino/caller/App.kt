package com.glasserino.caller

import android.app.Application
import com.glasserino.caller.di.repositories
import com.glasserino.caller.di.viewModels
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