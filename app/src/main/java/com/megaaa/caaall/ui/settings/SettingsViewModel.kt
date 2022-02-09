package com.megaaa.caaall.ui.settings

import androidx.databinding.ObservableBoolean
import com.megaaa.caaall.base.BaseViewModel
import com.megaaa.caaall.repository.PreferencesRepository

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