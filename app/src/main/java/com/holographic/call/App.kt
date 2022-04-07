package com.holographic.call

import android.app.Application
import com.holographic.call.di.repositories
import com.holographic.call.di.viewModels
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