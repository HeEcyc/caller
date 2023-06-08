package com.yee.zer.base

import androidx.lifecycle.MutableLiveData
import com.yee.zer.repository.LocaleRepository

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