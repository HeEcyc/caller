package com.sappy.callthemes.ui.language

import com.sappy.callthemes.base.BaseViewModel
import com.sappy.callthemes.repository.LocaleRepository

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