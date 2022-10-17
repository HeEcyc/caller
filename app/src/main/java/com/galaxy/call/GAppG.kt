package com.galaxy.call

import android.app.Application
import com.galaxy.call.di.repositories
import com.galaxy.call.di.viewModels
import org.koin.core.context.startKoin

class GAppG : Application() {

    companion object {
        lateinit var instance: GAppG
    }

    override fun onCreate() {
        println("")
        super.onCreate()
        println("")
        instance = this
        println("")
        startKoin { modules(repositories, viewModels) }
        println("")
    }

}