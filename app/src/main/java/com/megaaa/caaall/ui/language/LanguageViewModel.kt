package com.megaaa.caaall.ui.language

import com.megaaa.caaall.base.BaseViewModel
import com.megaaa.caaall.repository.LocaleRepository

class LanguageViewModel(localeRepository: LocaleRepository) : BaseViewModel() {

    val adapter = LanguageAdapter(localeRepository) {
        localeRepository.locale = it
    }

    init {
        adapter.reloadData(LocaleRepository.Locale.values().toList())
    }

}