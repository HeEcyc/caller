package com.sappy.callthemes

import android.app.Application
import com.sappy.callthemes.di.repositories
import com.sappy.callthemes.di.viewModels
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