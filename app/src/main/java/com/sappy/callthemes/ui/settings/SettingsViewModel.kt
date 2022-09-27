package com.sappy.callthemes.ui.settings

import androidx.databinding.ObservableBoolean
import com.sappy.callthemes.base.BaseViewModel
import com.sappy.callthemes.repository.LocaleRepository
import com.sappy.callthemes.repository.PermissionRepository
import com.sappy.callthemes.repository.PreferencesRepository

class SettingsViewModel(
    private val preferencesRepository: PreferencesRepository,
    val permissionRepository: PermissionRepository,
    val localeRepository: LocaleRepository
) : BaseViewModel() {

    val isFlashOn = ObservableBoolean(preferencesRepository.isFlashOn)
    val isAccelerometerOn = ObservableBoolean(preferencesRepository.isAccelerometerOn)

    fun onFlashClick() {
        preferencesRepository.isFlashOn = !preferencesRepository.isFlashOn
        isFlashOn.set(preferencesRepository.isFlashOn)
    }

    fun onAccelerometerClick() {
        preferencesRepository.isAccelerometerOn = !preferencesRepository.isAccelerometerOn
        isAccelerometerOn.set(preferencesRepository.isAccelerometerOn)
    }

}