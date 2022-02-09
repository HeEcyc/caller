package com.megaaa.caaall

import android.app.Application
import com.megaaa.caaall.di.repositories
import com.megaaa.caaall.di.viewModels
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