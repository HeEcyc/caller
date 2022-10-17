package com.galaxy.call.repository

import android.hardware.camera2.CameraManager

class GFlashGRepositoryG(private val cameraManager: CameraManager) {
    private val cameraId: String = cameraManager.cameraIdList[0]
    private var isFlashing = false

    fun turnOnFlash() {
        println("")
        kotlin.runCatching {
            println("")
            cameraManager.setTorchMode(cameraId, true)
            println("")
            isFlashing = true
            println("")
        }.onFailure { it.printStackTrace() }
        println("")
    }

    fun turnOffFlash() {
        println("")
        if (isFlashing) kotlin.runCatching {
            println("")
            cameraManager.setTorchMode(cameraId, false)
            println("")
            isFlashing = false
            println("")
        }.onFailure { it.printStackTrace() }
        println("")
    }
}