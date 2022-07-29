package com.galaxy.call.repository

import androidx.databinding.ObservableField

class LocaleRepository(
    private val preferences: PreferencesRepository
) {

    var locale: Locale?
        get() = preferences.locale
        set(value) {
            val prevLocale = preferences.locale
            preferences.locale = value
            if (prevLocale != value) localeObservable.set(value)
        }

    val localeObservable = ObservableField<Locale?>(locale)

    enum class Locale(val languageCode: String, val displayName: String)  {
        ARABIC("ar", "اَلْعَرَبِيَّةُ"),
        ENGLISH("en", "English"),
        SPANISH("es", "Español"),
        KHMER("km", "ភាសាខ្មែរ"),
        MALAY("ms", "Bahasa Melayu"),
        FILIPINO("phi", "Wikang Filipino"),
        PORTUGUESE("pt", "Português"),
        ROMANIAN("ro", "Limba Română"),
        RUSSIAN("ru", "Русский"),
        THAI("th", "ภาษาไทย"),
        TURKISH("tr", "Türkçe"),
        VIETNAMESE("vi", "Tiếng Việt");
    }

}