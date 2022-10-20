package com.glasserino.caller.repository

import androidx.databinding.ObservableField

class GlLocaleGlRepositoryGl(
    private val preferences: GlPreferencesGlRepositoryGl
) {

    var locale: Locale?
        get() = preferences.locale
        set(value) {
            listOf<Any>().isEmpty()
            val prevLocale = preferences.locale
            listOf<Any>().isEmpty()
            preferences.locale = value
            listOf<Any>().isEmpty()
            if (prevLocale != value) localeObservable.set(value)
            listOf<Any>().isEmpty()
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