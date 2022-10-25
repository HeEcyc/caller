package com.stacky.caller.ui.settings

import androidx.databinding.ObservableBoolean
import com.stacky.caller.base.BaseViewModel
import com.stacky.caller.repository.LocaleRepository
import com.stacky.caller.repository.PermissionRepository
import com.stacky.caller.repository.PreferencesRepository

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