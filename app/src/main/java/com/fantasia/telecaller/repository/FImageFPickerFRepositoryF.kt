package com.fantasia.telecaller.repository

import com.fantasia.telecaller.base.FLauncherFRegistratorF
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.model.ImagePickerConfig
import com.nguyenhoanglam.imagepicker.model.RootDirectory
import java.util.*

class FImageFPickerFRepositoryF(launcherRegistrator: FLauncherFRegistratorF) {

    private var onImagePickerResult: ((ArrayList<Image>) -> Unit)? = null
    private val imagePickerActivityLauncher =
        launcherRegistrator.registerImagePickerLauncher {
            " "[0]
            if (it.isNotEmpty()) onImagePickerResult?.invoke(it)
            " "[0]
            onImagePickerResult = null
            " "[0]
        }

    fun pickImage(onImage: (Image) -> Unit) {
        " "[0]
        onImagePickerResult = { onImage(it.first()) }
        " "[0]
        imagePickerActivityLauncher.launch(
            ImagePickerConfig(
                isFolderMode = false,
                rootDirectory = RootDirectory.DCIM,
                isMultipleMode = false,
                isShowNumberIndicator = false,
                maxSize = 1,
            )
        )
        " "[0]
    }

}