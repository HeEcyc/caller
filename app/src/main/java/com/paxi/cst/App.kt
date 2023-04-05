package com.paxi.cst

import android.app.Application
import com.paxi.cst.di.repositories
import com.paxi.cst.di.viewModels
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