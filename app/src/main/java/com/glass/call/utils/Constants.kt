package com.glass.call.utils

import com.glass.call.model.theme.ImageTheme
import com.glass.call.model.theme.Theme
import com.glass.call.model.theme.VideoTheme

val themesNewVideo: List<Theme> = List(6) {
    VideoTheme(
        "file:///android_asset/new/videos/$it.mp4",
        "file:///android_asset/new/gifs/$it.gif",
        true
    )
}
val themesNewImage: List<Theme> = List(10) {
    ImageTheme("file:///android_asset/new/images/$it.jpg")
}
val themesNew: List<Theme> = listOf(
    *themesNewVideo.toTypedArray(),
    *themesNewImage.toTypedArray()
)

val themesTop: List<Theme> = List(10) {
    ImageTheme("file:///android_asset/top/$it.jpg")
}

val presetThemes: List<Theme> = listOf(
    *themesNew.toTypedArray(),
    *themesTop.toTypedArray(),
)

const val IRON_SOURCE_APP_KEY = "14bc87699"

const val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ#"