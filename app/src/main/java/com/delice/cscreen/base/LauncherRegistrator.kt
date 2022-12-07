package com.delice.cscreen.base

import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerCallback
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerLauncher

interface LauncherRegistrator {

    fun <I, O> registerActivityResultLauncher(
        contract: ActivityResultContract<I, O>,
        callback: ActivityResultCallback<O>
    ): ActivityResultLauncher<I>

    fun registerImagePickerLauncher(
        callback: ImagePickerCallback
    ): ImagePickerLauncher

}