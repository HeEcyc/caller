package com.galala.lalaxy.ui.settings

import androidx.databinding.ObservableBoolean
import com.galala.lalaxy.base.GBaseGViewGModelG
import com.galala.lalaxy.repository.GLocaleGRepositoryG
import com.galala.lalaxy.repository.GPermissionGRepositoryG
import com.galala.lalaxy.repository.GPreferencesGRepositoryG

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