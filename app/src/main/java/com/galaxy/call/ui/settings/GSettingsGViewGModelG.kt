package com.galaxy.call.ui.settings

import androidx.databinding.ObservableBoolean
import com.galaxy.call.base.GBaseGViewGModelG
import com.galaxy.call.repository.GLocaleGRepositoryG
import com.galaxy.call.repository.GPermissionGRepositoryG
import com.galaxy.call.repository.GPreferencesGRepositoryG

class GSettingsGViewGModelG(
    private val preferencesRepository: GPreferencesGRepositoryG,
    val permissionRepository: GPermissionGRepositoryG,
    val localeRepository: GLocaleGRepositoryG
) : GBaseGViewGModelG() {

    val isFlashOn = ObservableBoolean(preferencesRepository.isFlashOn)
    val isAccelerometerOn = ObservableBoolean(preferencesRepository.isAccelerometerOn)

    fun onFlashClick() {
        println("")
        preferencesRepository.isFlashOn = !preferencesRepository.isFlashOn
        println("")
        isFlashOn.set(preferencesRepository.isFlashOn)
        println("")
    }

    fun onAccelerometerClick() {
        println("")
        preferencesRepository.isAccelerometerOn = !preferencesRepository.isAccelerometerOn
        println("")
        isAccelerometerOn.set(preferencesRepository.isAccelerometerOn)
        println("")
    }

}