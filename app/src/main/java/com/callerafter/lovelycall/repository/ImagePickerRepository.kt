package com.callerafter.lovelycall.repository

import com.callerafter.lovelycall.base.LauncherRegistrator
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.model.ImagePickerConfig
import com.nguyenhoanglam.imagepicker.model.RootDirectory
import java.util.*

class ImagePickerRepository(launcherRegistrator: LauncherRegistrator) {

    private var onImagePickerResult: ((ArrayList<Image>) -> Unit)? = null
    private val imagePickerActivityLauncher =
        launcherRegistrator.registerImagePickerLauncher {
            onImagePickerResult?.invoke(it)
            onImagePickerResult = null
        }

    fun pickImage(onImage: (Image) -> Unit) {
        onImagePickerResult = { onImage(it.first()) }
        imagePickerActivityLauncher.launch(
            ImagePickerConfig(
                isFolderMode = false,
                rootDirectory = RootDirectory.DCIM,
                isMultipleMode = false,
                isShowNumberIndicator = false,
                maxSize = 1,
            )
        )
    }

}