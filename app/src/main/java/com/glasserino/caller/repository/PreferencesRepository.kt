package com.glasserino.caller.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.glasserino.caller.repository.LocaleRepository.Locale
import java.lang.Exception

class PreferencesRepository(context: Context) {

    private val preferences = context.getSharedPreferences("caller", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LOCALE = "key_locale"
        private const val KEY_IS_FLASH_ON = "key_is_flash_on"
        private const val KEY_IS_ACCELEROMETER_ON = "key_is_accelerometer_on"
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

    @SuppressLint("CommitPrefEdits")
    private fun edit(block: SharedPreferences.Editor.() -> SharedPreferences.Editor) =
        preferences.edit().block().apply()

}