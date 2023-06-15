package com.docall.jocall.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.databinding.ObservableField
import com.docall.jocall.model.theme.ImageTheme
import com.docall.jocall.model.theme.Theme
import com.docall.jocall.repository.LocaleRepository.Locale
import com.docall.jocall.utils.presetThemes

class PreferencesRepository(context: Context) {

    private val preferences = context.getSharedPreferences("caller", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LOCALE = "key_locale"
        private const val KEY_IS_FLASH_ON = "key_is_flash_on"
        private const val KEY_IS_ACCELEROMETER_ON = "key_is_accelerometer_on"
        private const val KEY_HAS_BEEN_LAUNCHED_BEFORE = "key_has_been_launched_before"
        private const val KEY_THEME = "key_theme"
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

    var theme: Theme
        get() = preferences.getString(KEY_THEME, presetThemes.first().backgroundFile).let { themeFile ->
            presetThemes.firstOrNull { it.backgroundFile == themeFile } ?: ImageTheme(themeFile!!)
        }
        set(value) = edit {
            themeObservable.set(value)
            putString(KEY_THEME, value.backgroundFile)
        }
    val themeObservable = ObservableField(theme)

    @SuppressLint("CommitPrefEdits")
    private fun edit(block: SharedPreferences.Editor.() -> SharedPreferences.Editor) =
        preferences.edit().block().apply()

}