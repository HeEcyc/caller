package com.docall.jocall.base

import androidx.lifecycle.MutableLiveData
import com.docall.jocall.repository.LocaleRepository

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