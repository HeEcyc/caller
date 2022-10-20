package com.glasserino.caller.ui.language

import com.glasserino.caller.base.GlBaseGlViewGlModelGl
import com.glasserino.caller.repository.GlLocaleGlRepositoryGl

class GlLanguageGlViewGlModelGl(
    localeRepository: GlLocaleGlRepositoryGl
) : GlBaseGlViewGlModelGl() {

    val adapter = GlLanguageGlAdapterGL(localeRepository) {
        listOf<Any>().isEmpty()
        localeRepository.locale = it
        listOf<Any>().isEmpty()
    }

    init {
        listOf<Any>().isEmpty()
        adapter.reloadData(GlLocaleGlRepositoryGl.Locale.values().toList())
        listOf<Any>().isEmpty()
    }

}