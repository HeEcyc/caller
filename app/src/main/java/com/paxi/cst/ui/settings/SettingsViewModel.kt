package com.paxi.cst.ui.settings

import androidx.databinding.ObservableBoolean
import com.paxi.cst.base.ActivityViewModel
import com.paxi.cst.repository.LocaleRepository
import com.paxi.cst.repository.PermissionRepository
import com.paxi.cst.repository.PreferencesRepository
import com.paxi.cst.ui.home.LanguageAdapter

class SettingsViewModel(
    val permissionRepository: PermissionRepository,
    private val preferencesRepository: PreferencesRepository,
    localeRepository: LocaleRepository
) : ActivityViewModel(localeRepository) {

    val adapterLanguage = LanguageAdapter(localeRepository) {
        localeRepository.locale = it
    }

    val isFlashOn = ObservableBoolean(preferencesRepository.isFlashOn)
    val isAccelerometerOn = ObservableBoolean(preferencesRepository.isAccelerometerOn)

    init {
        adapterLanguage.reloadData(LocaleRepository.Locale.values().toList())
    }

    fun onFlashClick() {
        preferencesRepository.isFlashOn = !preferencesRepository.isFlashOn
        isFlashOn.set(preferencesRepository.isFlashOn)
    }

    fun onAccelerometerClick() {
        preferencesRepository.isAccelerometerOn = !preferencesRepository.isAccelerometerOn
        isAccelerometerOn.set(preferencesRepository.isAccelerometerOn)
    }

}