package com.paxi.cst

import com.app.sdk.AppApplication
import com.paxi.cst.di.repositories
import com.paxi.cst.di.viewModels
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