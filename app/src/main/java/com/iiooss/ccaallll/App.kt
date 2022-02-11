package com.iiooss.ccaallll

import android.app.Application
import com.iiooss.ccaallll.di.repositories
import com.iiooss.ccaallll.di.viewModels
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