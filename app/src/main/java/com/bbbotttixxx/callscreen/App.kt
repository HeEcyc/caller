package com.bbbotttixxx.callscreen

import com.app.sdk.AppApplication
import com.bbbotttixxx.callscreen.di.repositories
import com.bbbotttixxx.callscreen.di.viewModels
import org.koin.core.context.startKoin

class App : AppApplication() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin { modules(repositories, viewModels) }
    }

}