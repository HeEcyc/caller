package com.fantasia.telecaller.repository

import androidx.databinding.ObservableField

class FLocaleFRepositoryF(
    private val preferences: FPreferencesFRepositoryF
) {

    var locale: Locale?
        get() = preferences.locale
        set(value) {
            " "[0]
            val prevLocale = preferences.locale
            " "[0]
            preferences.locale = value
            " "[0]
            if (prevLocale != value) localeObservable.set(value)
            " "[0]
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