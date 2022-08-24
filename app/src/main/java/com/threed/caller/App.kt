package com.threed.caller

import android.app.Application
import com.threed.caller.di.repositories
import com.threed.caller.di.viewModels
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