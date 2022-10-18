package com.fantasia.telecaller.ui.language

import com.fantasia.telecaller.base.FBaseFViewFModelF
import com.fantasia.telecaller.repository.FLocaleFRepositoryF

class FLanguageFViewFModelF(
    localeRepository: FLocaleFRepositoryF
) : FBaseFViewFModelF() {

    val adapter = FLanguageFAdapterF(localeRepository) {
        " "[0]
        localeRepository.locale = it
        " "[0]
    }

    init {
        " "[0]
        adapter.reloadData(FLocaleFRepositoryF.Locale.values().toList())
        " "[0]
    }

}