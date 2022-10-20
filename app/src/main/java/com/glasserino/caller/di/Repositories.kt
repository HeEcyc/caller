package com.glasserino.caller.di

import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.os.Vibrator
import android.telecom.TelecomManager
import android.telephony.SubscriptionManager
import androidx.room.Room
import com.glasserino.caller.GlAppGl
import com.glasserino.caller.base.GlLauncherGlRegistratorGl
import com.glasserino.caller.repository.*
import com.glasserino.caller.repository.call.AudioManagerRepository
import com.glasserino.caller.repository.call.CallRepository
import com.glasserino.caller.repository.database.DB
import org.koin.dsl.module

val repositories = module {
    single { GlPreferencesGlRepositoryGl(GlAppGl.instance) }
    single {
        GlThemeGlRepositoryGl(
            Room.databaseBuilder(GlAppGl.instance, DB::class.java, "themesDB").build().getThemeDao()
        )
    }
    single { GlContactsGlRepositoryGl(GlAppGl.instance) }
    single { GlLocaleGlRepositoryGl(get()) }
    single { GlFileGlRepositoryGL() }
    single { AudioManagerRepository(GlAppGl.instance.getSystemService(AudioManager::class.java)) }
    single { CallRepository(
        get(),
        get(),
        get(),
        GlAppGl.instance.getSystemService(SubscriptionManager::class.java),
        GlAppGl.instance.getSystemService(TelecomManager::class.java)
    ) }
    single { GlVibrationGlRepositoryGl(GlAppGl.instance.getSystemService(Vibrator::class.java)) }
    single { GlFlashGlRepositoryGl(GlAppGl.instance.getSystemService(CameraManager::class.java)) }
    factory { (lr: GlLauncherGlRegistratorGl) -> GlPermissionGlRepositoryGl(lr) }
    factory { (lr: GlLauncherGlRegistratorGl) -> GlImageGlPickerGlRepositoryGl(lr) }
}