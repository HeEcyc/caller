package com.galala.lalaxy.repository

import androidx.databinding.ObservableField

class GLocaleGRepositoryG(
    private val preferences: GPreferencesGRepositoryG
) {

    var locale: Locale?
        get() = preferences.locale
        set(value) {
            println("")
            val prevLocale = preferences.locale
            println("")
            preferences.locale = value
            println("")
            if (prevLocale != value) localeObservable.set(value)
            println("")
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