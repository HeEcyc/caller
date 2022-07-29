package com.galaxy.call.ui.settings

import androidx.databinding.ObservableBoolean
import com.galaxy.call.base.BaseViewModel
import com.galaxy.call.repository.LocaleRepository
import com.galaxy.call.repository.PermissionRepository
import com.galaxy.call.repository.PreferencesRepository

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