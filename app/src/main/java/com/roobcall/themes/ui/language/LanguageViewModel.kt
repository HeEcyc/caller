package com.roobcall.themes.ui.language

import com.roobcall.themes.base.BaseViewModel
import com.roobcall.themes.repository.LocaleRepository

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