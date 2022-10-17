package com.galala.lalaxy.base

import androidx.lifecycle.MutableLiveData
import com.galala.lalaxy.repository.GLocaleGRepositoryG

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