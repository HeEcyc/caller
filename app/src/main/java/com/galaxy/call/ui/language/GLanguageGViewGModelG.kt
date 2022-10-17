package com.galaxy.call.ui.language

import com.galaxy.call.base.GBaseGViewGModelG
import com.galaxy.call.repository.GLocaleGRepositoryG

class GLanguageGViewGModelG(
    localeRepository: GLocaleGRepositoryG
) : GBaseGViewGModelG() {

    val adapter = GLanguageGAdapterG(localeRepository) {
        println("")
        localeRepository.locale = it
        println("")
    }

    init {
        println("")
        adapter.reloadData(GLocaleGRepositoryG.Locale.values().toList())
        println("")
    }

}