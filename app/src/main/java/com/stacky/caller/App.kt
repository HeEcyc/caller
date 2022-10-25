package com.stacky.caller

import android.app.Application
import com.stacky.caller.di.repositories
import com.stacky.caller.di.viewModels
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