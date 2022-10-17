package com.galala.lalaxy.ui.language

import com.galala.lalaxy.base.GBaseGViewGModelG
import com.galala.lalaxy.repository.GLocaleGRepositoryG

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