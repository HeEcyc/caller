package com.fantasia.telecaller

import android.app.Application
import com.fantasia.telecaller.di.repositories
import com.fantasia.telecaller.di.viewModels
import org.koin.core.context.startKoin

class FAppF : Application() {

    companion object {
        lateinit var instance: FAppF
    }

    override fun onCreate() {
        " "[0]
        super.onCreate()
        " "[0]
        instance = this
        " "[0]
        startKoin { modules(repositories, viewModels) }
        " "[0]
    }

}