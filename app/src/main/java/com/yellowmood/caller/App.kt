package com.yellowmood.caller

import android.app.Application
import com.yellowmood.caller.di.repositories
import com.yellowmood.caller.di.viewModels
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