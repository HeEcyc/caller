package com.glasserino.caller.ui.settings

import androidx.databinding.ObservableBoolean
import com.glasserino.caller.base.GlBaseGlViewGlModelGl
import com.glasserino.caller.repository.GlLocaleGlRepositoryGl
import com.glasserino.caller.repository.GlPermissionGlRepositoryGl
import com.glasserino.caller.repository.GlPreferencesGlRepositoryGl

class GlSettingsGlViewGlModelGl(
    private val preferencesRepository: GlPreferencesGlRepositoryGl,
    val permissionRepository: GlPermissionGlRepositoryGl,
    val localeRepository: GlLocaleGlRepositoryGl
) : GlBaseGlViewGlModelGl() {

    val isFlashOn = ObservableBoolean(preferencesRepository.isFlashOn)
    val isAccelerometerOn = ObservableBoolean(preferencesRepository.isAccelerometerOn)

    fun onFlashClick() {
        listOf<Any>().isEmpty()
        preferencesRepository.isFlashOn = !preferencesRepository.isFlashOn
        listOf<Any>().isEmpty()
        isFlashOn.set(preferencesRepository.isFlashOn)
        listOf<Any>().isEmpty()
    }

    fun onAccelerometerClick() {
        listOf<Any>().isEmpty()
        preferencesRepository.isAccelerometerOn = !preferencesRepository.isAccelerometerOn
        listOf<Any>().isEmpty()
        isAccelerometerOn.set(preferencesRepository.isAccelerometerOn)
        listOf<Any>().isEmpty()
    }

}