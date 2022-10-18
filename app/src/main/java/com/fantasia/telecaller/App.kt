package com.fantasia.telecaller

import android.app.Application
import com.fantasia.telecaller.di.repositories
import com.fantasia.telecaller.di.viewModels
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