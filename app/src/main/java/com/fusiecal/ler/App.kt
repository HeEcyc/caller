package com.fusiecal.ler

import android.app.Application
import com.fusiecal.ler.di.repositories
import com.fusiecal.ler.di.viewModels
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