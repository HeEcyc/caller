package com.vefercal.ler.ui.language

import com.vefercal.ler.base.BaseViewModel
import com.vefercal.ler.repository.LocaleRepository

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