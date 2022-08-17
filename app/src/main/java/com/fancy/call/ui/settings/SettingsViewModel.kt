package com.fancy.call.ui.settings

import androidx.databinding.ObservableBoolean
import com.fancy.call.base.BaseViewModel
import com.fancy.call.repository.LocaleRepository
import com.fancy.call.repository.PermissionRepository
import com.fancy.call.repository.PreferencesRepository

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