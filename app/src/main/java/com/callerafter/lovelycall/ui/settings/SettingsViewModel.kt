package com.callerafter.lovelycall.ui.settings

import androidx.databinding.ObservableBoolean
import com.callerafter.lovelycall.base.BaseViewModel
import com.callerafter.lovelycall.repository.PreferencesRepository

class SettingsViewModel(
    private val preferencesRepository: PreferencesRepository
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