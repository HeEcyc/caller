package com.galaxy.call.di

import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.os.Vibrator
import android.telecom.TelecomManager
import android.telephony.SubscriptionManager
import androidx.room.Room
import com.galaxy.call.GAppG
import com.galaxy.call.base.GLauncherGRegistratorG
import com.galaxy.call.repository.*
import com.galaxy.call.repository.call.AudioManagerRepository
import com.galaxy.call.repository.call.GCallGRepositoryG
import com.galaxy.call.repository.database.DB
import org.koin.dsl.module

val repositories = module {
    single { GPreferencesGRepositoryG(GAppG.instance) }
    single {
        GThemeGRepositoryG(
            Room.databaseBuilder(GAppG.instance, DB::class.java, "themesDB").build().getThemeDao()
        )
    }
    single { GContactsGRepositoryG(GAppG.instance) }
    single { GLocaleGRepositoryG(get()) }
    single { GFileGRepositoryG() }
    single { AudioManagerRepository(GAppG.instance.getSystemService(AudioManager::class.java)) }
    single { GCallGRepositoryG(
        get(),
        get(),
        get(),
        GAppG.instance.getSystemService(SubscriptionManager::class.java),
        GAppG.instance.getSystemService(TelecomManager::class.java)
    ) }
    single { GVibrationGRepositoryG(GAppG.instance.getSystemService(Vibrator::class.java)) }
    single { GFlashGRepositoryG(GAppG.instance.getSystemService(CameraManager::class.java)) }
    factory { (lr: GLauncherGRegistratorG) -> GPermissionGRepositoryG(lr) }
    factory { (lr: GLauncherGRegistratorG) -> GImageGPickerGRepositoryG(lr) }
}