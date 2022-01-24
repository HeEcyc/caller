package com.callerafter.lovelycall.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.callerafter.lovelycall.repository.LocaleRepository.Locale
import java.lang.Exception

class PreferencesRepository(context: Context) {

    private val preferences = context.getSharedPreferences("caller", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LOCALE = "key_locale"
    }

    var locale: Locale?
        get() = try {
            preferences.getString(KEY_LOCALE, null)?.let { Locale.valueOf(it) }
        } catch (e: Exception) { null }
        set(value) = edit { putString(KEY_LOCALE, value?.name) }

    @SuppressLint("CommitPrefEdits")
    private fun edit(block: SharedPreferences.Editor.() -> SharedPreferences.Editor) =
        preferences.edit().block().apply()

}