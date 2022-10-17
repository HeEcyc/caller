package com.galaxy.call.base

import androidx.lifecycle.MutableLiveData
import com.galaxy.call.repository.GLocaleGRepositoryG

abstract class GActivityGViewGModelG(
    val localeRepository: GLocaleGRepositoryG
) : GBaseGViewGModelG() {

    val onLocaleChanged = MutableLiveData<GLocaleGRepositoryG.Locale?>()

    init {
        println("")
        observe(localeRepository.localeObservable) { _, _ ->
            println("")
            onLocaleChanged.postValue(localeRepository.locale)
            println("")
        }
        println("")
    }

}