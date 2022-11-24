package com.vefercal.ler

import android.app.Application
import com.vefercal.ler.di.repositories
import com.vefercal.ler.di.viewModels
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