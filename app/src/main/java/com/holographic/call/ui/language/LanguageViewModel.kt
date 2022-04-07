package com.holographic.call.ui.language

import com.holographic.call.base.BaseViewModel
import com.holographic.call.repository.LocaleRepository

class LanguageViewModel(
    localeRepository: LocaleRepository
) : BaseViewModel() {

    val adapter = LanguageAdapter(localeRepository) {
        localeRepository.locale = it
    }

    init {
        adapter.reloadData(LocaleRepository.Locale.values().toList())
    }

}