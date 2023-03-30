package com.bbbotttixxx.callscreen

import android.app.Application
import com.bbbotttixxx.callscreen.di.repositories
import com.bbbotttixxx.callscreen.di.viewModels
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