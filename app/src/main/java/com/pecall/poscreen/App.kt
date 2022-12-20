package com.pecall.poscreen

import android.app.Application
import com.pecall.poscreen.di.repositories
import com.pecall.poscreen.di.viewModels
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