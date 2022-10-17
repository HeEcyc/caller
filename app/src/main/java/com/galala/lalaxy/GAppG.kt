package com.galala.lalaxy

import android.app.Application
import com.galala.lalaxy.di.repositories
import com.galala.lalaxy.di.viewModels
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