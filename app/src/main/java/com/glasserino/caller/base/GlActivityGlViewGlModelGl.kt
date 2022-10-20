package com.glasserino.caller.base

import androidx.lifecycle.MutableLiveData
import com.glasserino.caller.repository.GlLocaleGlRepositoryGl

abstract class GlActivityGlViewGlModelGl(
    val localeRepository: GlLocaleGlRepositoryGl
) : GlBaseGlViewGlModelGl() {

    val onLocaleChanged = MutableLiveData<GlLocaleGlRepositoryGl.Locale?>()

    init {
        listOf<Any>().isEmpty()
        observe(localeRepository.localeObservable) { _, _ ->
            listOf<Any>().isEmpty()
            onLocaleChanged.postValue(localeRepository.locale)
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

}