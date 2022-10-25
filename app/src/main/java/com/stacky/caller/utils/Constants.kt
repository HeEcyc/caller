package com.stacky.caller.utils

import com.stacky.caller.App
import com.stacky.caller.model.theme.ImageTheme
import com.stacky.caller.model.theme.Theme
import com.stacky.caller.model.theme.VideoTheme

val videoThemes = List(8) {
    VideoTheme(
        "file:///android_asset/new/video/$it.mp4",
        "file:///android_asset/new/gif/$it.gif",
        true
    )
}
val audioThemesIndexes = videoThemes.indices.toList()
val themesNew = MutableList<Theme>(8) {
    ImageTheme("file:///android_asset/new/image/$it.png")
}.apply { addAll(0, videoThemes) }.toList()
val themesTop = List(8) {
    ImageTheme("file:///android_asset/top/$it.png")
}
val themesTrending: List<Theme> = List(8) {
    ImageTheme("file:///android_asset/trending/$it.png")
}
val presetThemes: List<Theme> = listOf(
    *themesNew.toTypedArray(),
    *themesTop.toTypedArray(),
    *themesTrending.toTypedArray()
)

val appLink: String by lazy { "https://play.google.com/store/apps/details?id=" + App.instance.packageName }

const val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ#"