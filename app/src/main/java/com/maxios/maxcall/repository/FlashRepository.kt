package com.maxios.maxcall.repository

import android.hardware.camera2.CameraManager

class FlashRepository(private val cameraManager: CameraManager) {
    private val cameraId: String = cameraManager.cameraIdList[0]
    private var isFlashing = false

    fun turnOnFlash() {
        kotlin.runCatching {
            cameraManager.setTorchMode(cameraId, true)
            isFlashing = true
        }.onFailure { it.printStackTrace() }
    }

    fun turnOffFlash() {
        if (isFlashing) kotlin.runCatching {
            cameraManager.setTorchMode(cameraId, false)
            isFlashing = false
        }.onFailure { it.printStackTrace() }
    }
}