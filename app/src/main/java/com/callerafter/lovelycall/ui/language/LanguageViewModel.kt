package com.callerafter.lovelycall.ui.language

import com.callerafter.lovelycall.base.BaseViewModel
import com.callerafter.lovelycall.repository.LocaleRepository

class LanguageViewModel(localeRepository: LocaleRepository) : BaseViewModel() {

    val adapter = LanguageAdapter(localeRepository) {
        localeRepository.locale = it
    }

    init {
        adapter.reloadData(LocaleRepository.Locale.values().toList())
    }

}