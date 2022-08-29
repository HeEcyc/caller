package com.threed.caller.utils

import com.threed.caller.App
import com.threed.caller.model.theme.ImageTheme
import com.threed.caller.model.theme.Theme
import com.threed.caller.model.theme.VideoTheme

val videoThemes = List(8) {
    VideoTheme(
        "file:///android_asset/top/video/$it.mp4",
        "file:///android_asset/top/gif/$it.gif",
        true
    )
}
val audioThemesIndexes = videoThemes.indices.toList()
val themesTop = MutableList<Theme>(8) {
    ImageTheme("file:///android_asset/top/image/$it.png")
}.apply { addAll(0, videoThemes) }.toList()
val themesNew = List(8) {
    ImageTheme("file:///android_asset/new/$it.png")
}
val themesRecommended: List<Theme> = List(8) {
    ImageTheme("file:///android_asset/recommended/$it.png")
}
val presetThemes: List<Theme> = listOf(
    *themesTop.toTypedArray(),
    *themesNew.toTypedArray(),
    *themesRecommended.toTypedArray()
)

val appLink: String by lazy { "https://play.google.com/store/apps/details?id=" + App.instance.packageName }

const val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ#"