package com.iiooss.ccaallll.ui.settings

import androidx.databinding.ObservableBoolean
import com.iiooss.ccaallll.base.BaseViewModel
import com.iiooss.ccaallll.repository.LocaleRepository
import com.iiooss.ccaallll.repository.PermissionRepository
import com.iiooss.ccaallll.repository.PreferencesRepository

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
