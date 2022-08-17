package com.fancy.call.ui.language

import com.fancy.call.base.BaseViewModel
import com.fancy.call.repository.LocaleRepository

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