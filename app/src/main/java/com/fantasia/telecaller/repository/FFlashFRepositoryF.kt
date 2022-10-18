package com.fantasia.telecaller.repository

import android.hardware.camera2.CameraManager

class FFlashFRepositoryF(private val cameraManager: CameraManager) {
    private val cameraId: String = cameraManager.cameraIdList[0]
    private var isFlashing = false

    fun turnOnFlash() {
        " "[0]
        kotlin.runCatching {
            " "[0]
            cameraManager.setTorchMode(cameraId, true)
            " "[0]
            isFlashing = true
            " "[0]
        }.onFailure { it.printStackTrace() }
        " "[0]
    }

    fun turnOffFlash() {
        " "[0]
        if (isFlashing) kotlin.runCatching {
            " "[0]
            cameraManager.setTorchMode(cameraId, false)
            " "[0]
            isFlashing = false
            " "[0]
        }.onFailure { it.printStackTrace() }
        " "[0]
    }
}