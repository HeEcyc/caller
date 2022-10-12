package com.sappy.callthemes.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.sappy.callthemes.repository.LocaleRepository.Locale
import java.lang.Exception

class PreferencesRepository(context: Context) {

    private val preferences = context.getSharedPreferences("caller", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LOCALE = "key_locale"
        private const val KEY_IS_FLASH_ON = "key_is_flash_on"
        private const val KEY_IS_ACCELEROMETER_ON = "key_is_accelerometer_on"
        private const val KEY_HAS_BEEN_LAUNCHED_BEFORE = "key_has_been_launched_before"
        private const val KEY_FIRST_LAUNCH_MILLIS = "key_first_launch_millis"
        private const val KEY_SENT_FIRST_NOTIFICATION = "sent_first_notification"
    }

    var locale: Locale?
        get() = try {
            preferences.getString(KEY_LOCALE, null)?.let { Locale.valueOf(it) }
        } catch (e: Exception) { null }
        set(value) = edit { putString(KEY_LOCALE, value?.name) }

    var isFlashOn: Boolean
        get() = preferences.getBoolean(KEY_IS_FLASH_ON, true)
        set(value) = edit { putBoolean(KEY_IS_FLASH_ON, value) }

    var isAccelerometerOn: Boolean
        get() = preferences.getBoolean(KEY_IS_ACCELEROMETER_ON, true)
        set(value) = edit { putBoolean(KEY_IS_ACCELEROMETER_ON, value) }

    var hasBeenLaunchedBefore: Boolean
        get() = preferences.getBoolean(KEY_HAS_BEEN_LAUNCHED_BEFORE, false)
        set(value) = edit { putBoolean(KEY_HAS_BEEN_LAUNCHED_BEFORE, value) }

    var firstLaunchMillis: Long
        get() = preferences.getLong(KEY_FIRST_LAUNCH_MILLIS, -1)
        set(value) = edit { putLong(KEY_FIRST_LAUNCH_MILLIS, value) }

    var sentFirstNotification: Boolean
        get() = preferences.getBoolean(KEY_SENT_FIRST_NOTIFICATION, false)
        set(value) = edit { putBoolean(KEY_SENT_FIRST_NOTIFICATION, value) }

    @SuppressLint("CommitPrefEdits")
    private fun edit(block: SharedPreferences.Editor.() -> SharedPreferences.Editor) =
        preferences.edit().block().apply()

}