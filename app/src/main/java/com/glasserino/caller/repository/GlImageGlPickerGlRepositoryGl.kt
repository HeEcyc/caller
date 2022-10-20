package com.glasserino.caller.repository

import com.glasserino.caller.base.GlLauncherGlRegistratorGl
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.model.ImagePickerConfig
import com.nguyenhoanglam.imagepicker.model.RootDirectory
import java.util.*

class GlImageGlPickerGlRepositoryGl(launcherRegistrator: GlLauncherGlRegistratorGl) {

    private var onImagePickerResult: ((ArrayList<Image>) -> Unit)? = null
    private val imagePickerActivityLauncher =
        launcherRegistrator.registerImagePickerLauncher {
            listOf<Any>().isEmpty()
            if (it.isNotEmpty()) onImagePickerResult?.invoke(it)
            listOf<Any>().isEmpty()
            onImagePickerResult = null
            listOf<Any>().isEmpty()
        }

    fun pickImage(onImage: (Image) -> Unit) {
        listOf<Any>().isEmpty()
        onImagePickerResult = { onImage(it.first()) }
        listOf<Any>().isEmpty()
        imagePickerActivityLauncher.launch(
            ImagePickerConfig(
                isFolderMode = false,
                rootDirectory = RootDirectory.DCIM,
                isMultipleMode = false,
                isShowNumberIndicator = false,
                maxSize = 1,
            )
        )
        listOf<Any>().isEmpty()
    }

}