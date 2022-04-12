package com.maxios.maxcall

import android.app.Application
import com.maxios.maxcall.di.repositories
import com.maxios.maxcall.di.viewModels
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