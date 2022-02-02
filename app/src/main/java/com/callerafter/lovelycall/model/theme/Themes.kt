package com.callerafter.lovelycall.model.theme

import com.callerafter.lovelycall.utils.presetThemes
import java.io.Serializable

sealed class Theme(val backgroundFile: String) : Serializable {

    abstract val previewFile: String
    abstract val isCustom: Boolean

    override fun equals(other: Any?): Boolean {
        if (other === null) return false
        if (other === this) return true
        if (other !is Theme) return false
        if (other.hashCode() != hashCode()) return false
        return other.backgroundFile == backgroundFile
    }

    override fun hashCode(): Int = backgroundFile.hashCode()

}

object NewTheme : Theme("") {

    override val previewFile: String get() = throw NotImplementedError("not needed")
    override val isCustom: Boolean get() = throw NotImplementedError("not needed")

}

class ImageTheme(pictureFile: String) : Theme(pictureFile) {

    override val previewFile: String = backgroundFile
    override val isCustom: Boolean by lazy { !presetThemes.contains(this) }

}

class VideoTheme(videoFile: String, gifFile: String) : Theme(videoFile) {

    override val previewFile: String = gifFile
    override val isCustom: Boolean = false

}