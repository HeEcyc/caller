package com.megaaa.caaall.base

import androidx.lifecycle.MutableLiveData
import com.megaaa.caaall.repository.LocaleRepository

abstract class ActivityViewModel(
    val localeRepository: LocaleRepository
) : BaseViewModel() {

    val onLocaleChanged = MutableLiveData<LocaleRepository.Locale?>()

    init {
        observe(localeRepository.localeObservable) { _, _ ->
            onLocaleChanged.postValue(localeRepository.locale)
        }
    }

}