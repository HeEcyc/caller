package com.iiooss.ccaallll.ui.language

import com.iiooss.ccaallll.base.BaseViewModel
import com.iiooss.ccaallll.repository.LocaleRepository

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