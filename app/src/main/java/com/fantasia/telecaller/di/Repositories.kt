package com.fantasia.telecaller.di

import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.os.Vibrator
import android.telecom.TelecomManager
import android.telephony.SubscriptionManager
import androidx.room.Room
import com.fantasia.telecaller.FAppF
import com.fantasia.telecaller.base.FLauncherFRegistratorF
import com.fantasia.telecaller.repository.*
import com.fantasia.telecaller.repository.call.AudioManagerRepository
import com.fantasia.telecaller.repository.call.CallRepository
import com.fantasia.telecaller.repository.database.DB
import org.koin.dsl.module

val repositories = module {
    single { FPreferencesFRepositoryF(FAppF.instance) }
    single {
        FThemeFRepositoryF(
            Room.databaseBuilder(FAppF.instance, DB::class.java, "themesDB").build().getThemeDao()
        )
    }
    single { FContactsFRepositoryF(FAppF.instance) }
    single { FLocaleFRepositoryF(get()) }
    single { FFileFRepositoryF() }
    single { AudioManagerRepository(FAppF.instance.getSystemService(AudioManager::class.java)) }
    single { CallRepository(
        get(),
        get(),
        get(),
        FAppF.instance.getSystemService(SubscriptionManager::class.java),
        FAppF.instance.getSystemService(TelecomManager::class.java)
    ) }
    single { FVibrationFRepositoryF(FAppF.instance.getSystemService(Vibrator::class.java)) }
    single { FFlashFRepositoryF(FAppF.instance.getSystemService(CameraManager::class.java)) }
    factory { (lr: FLauncherFRegistratorF) -> FPermissionFRepositoryF(lr) }
    factory { (lr: FLauncherFRegistratorF) -> FImageFPickerFRepositoryF(lr) }
}