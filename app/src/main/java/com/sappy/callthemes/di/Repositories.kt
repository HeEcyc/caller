package com.sappy.callthemes.di

import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.os.Vibrator
import android.telecom.TelecomManager
import android.telephony.SubscriptionManager
import androidx.room.Room
import com.sappy.callthemes.App
import com.sappy.callthemes.base.LauncherRegistrator
import com.sappy.callthemes.repository.*
import com.sappy.callthemes.repository.call.AudioManagerRepository
import com.sappy.callthemes.repository.call.CallRepository
import com.sappy.callthemes.repository.database.DB
import org.koin.dsl.module

val repositories = module {
    single { PreferencesRepository(App.instance) }
    single {
        ThemeRepository(
            Room.databaseBuilder(App.instance, DB::class.java, "themesDB").build().getThemeDao()
        )
    }
    single { ContactsRepository(App.instance) }
    single { LocaleRepository(get()) }
    single { FileRepository() }
    single { AudioManagerRepository(App.instance.getSystemService(AudioManager::class.java)) }
    single { CallRepository(
        get(),
        get(),
        get(),
        App.instance.getSystemService(SubscriptionManager::class.java),
        App.instance.getSystemService(TelecomManager::class.java)
    ) }
    single { VibrationRepository(App.instance.getSystemService(Vibrator::class.java)) }
    single { FlashRepository(App.instance.getSystemService(CameraManager::class.java)) }
    factory { (lr: LauncherRegistrator) -> PermissionRepository(lr) }
    factory { (lr: LauncherRegistrator) -> ImagePickerRepository(lr) }
}