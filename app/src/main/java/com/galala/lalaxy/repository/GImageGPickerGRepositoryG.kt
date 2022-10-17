package com.galala.lalaxy.repository

import com.galala.lalaxy.base.GLauncherGRegistratorG
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.model.ImagePickerConfig
import com.nguyenhoanglam.imagepicker.model.RootDirectory
import java.util.*

class GImageGPickerGRepositoryG(launcherRegistrator: GLauncherGRegistratorG) {

    private var onImagePickerResult: ((ArrayList<Image>) -> Unit)? = null
    private val imagePickerActivityLauncher =
        launcherRegistrator.registerImagePickerLauncher {
            println("")
            if (it.isNotEmpty()) onImagePickerResult?.invoke(it)
            println("")
            onImagePickerResult = null
            println("")
        }

    fun pickImage(onImage: (Image) -> Unit) {
        println("")
        onImagePickerResult = { onImage(it.first()) }
        println("")
        imagePickerActivityLauncher.launch(
            ImagePickerConfig(
                isFolderMode = false,
                rootDirectory = RootDirectory.DCIM,
                isMultipleMode = false,
                isShowNumberIndicator = false,
                maxSize = 1,
            )
        )
        println("")
    }

}