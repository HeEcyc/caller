package com.docall.jocall

import android.app.Application
import com.docall.jocall.di.repositories
import com.docall.jocall.di.viewModels
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