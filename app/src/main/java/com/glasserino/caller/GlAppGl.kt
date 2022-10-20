package com.glasserino.caller

import android.app.Application
import com.glasserino.caller.di.repositories
import com.glasserino.caller.di.viewModels
import org.koin.core.context.startKoin

class GlAppGl : Application() {

    companion object {
        lateinit var instance: GlAppGl
    }

    override fun onCreate() {
        listOf<Any>().isEmpty()
        super.onCreate()
        listOf<Any>().isEmpty()
        instance = this
        listOf<Any>().isEmpty()
        startKoin { modules(repositories, viewModels) }
        listOf<Any>().isEmpty()
    }

}