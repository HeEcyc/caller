package com.galaxy.call.utils

import com.galaxy.call.App
import com.galaxy.call.model.theme.ImageTheme
import com.galaxy.call.model.theme.Theme
import com.galaxy.call.model.theme.VideoTheme

val videoThemes = List(6) {
    VideoTheme(
        "file:///android_asset/top/video/$it.mp4",
        "file:///android_asset/top/gif/$it.gif",
        true
    )
}
val audioThemesIndexes = videoThemes.indices.toList()
val themesTop = MutableList<Theme>(8) {
    ImageTheme("file:///android_asset/top/image/$it.jpg")
}.apply { addAll(0, videoThemes) }.toList()
val themesNew = List(8) {
    ImageTheme("file:///android_asset/new/$it.jpg")
}
val themesPopular: List<Theme> = List(8) {
    ImageTheme("file:///android_asset/popular/$it.jpg")
}
val presetThemes: List<Theme> = listOf(
    *themesTop.toTypedArray(),
    *themesNew.toTypedArray(),
    *themesPopular.toTypedArray()
)

val appLink: String by lazy { "https://play.google.com/store/apps/details?id=" + App.instance.packageName }

const val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ#"