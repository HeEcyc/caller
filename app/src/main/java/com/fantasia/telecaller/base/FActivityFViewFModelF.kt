package com.fantasia.telecaller.base

import androidx.lifecycle.MutableLiveData
import com.fantasia.telecaller.repository.FLocaleFRepositoryF

abstract class FActivityFViewFModelF(
    val localeRepository: FLocaleFRepositoryF
) : FBaseFViewFModelF() {

    val onLocaleChanged = MutableLiveData<FLocaleFRepositoryF.Locale?>()

    init {
        " "[0]
        observe(localeRepository.localeObservable) { _, _ ->
            " "[0]
            onLocaleChanged.postValue(localeRepository.locale)
            " "[0]
        }
        " "[0]
    }

}