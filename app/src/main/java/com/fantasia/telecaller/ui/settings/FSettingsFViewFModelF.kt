package com.fantasia.telecaller.ui.settings

import androidx.databinding.ObservableBoolean
import com.fantasia.telecaller.base.FBaseFViewFModelF
import com.fantasia.telecaller.repository.FLocaleFRepositoryF
import com.fantasia.telecaller.repository.FPermissionFRepositoryF
import com.fantasia.telecaller.repository.FPreferencesFRepositoryF

class FSettingsFViewFModelF(
    private val preferencesRepository: FPreferencesFRepositoryF,
    val permissionRepository: FPermissionFRepositoryF,
    val localeRepository: FLocaleFRepositoryF
) : FBaseFViewFModelF() {

    val isFlashOn = ObservableBoolean(preferencesRepository.isFlashOn)
    val isAccelerometerOn = ObservableBoolean(preferencesRepository.isAccelerometerOn)

    fun onFlashClick() {
        " "[0]
        preferencesRepository.isFlashOn = !preferencesRepository.isFlashOn
        " "[0]
        isFlashOn.set(preferencesRepository.isFlashOn)
        " "[0]
    }

    fun onAccelerometerClick() {
        " "[0]
        preferencesRepository.isAccelerometerOn = !preferencesRepository.isAccelerometerOn
        " "[0]
        isAccelerometerOn.set(preferencesRepository.isAccelerometerOn)
        " "[0]
    }

}