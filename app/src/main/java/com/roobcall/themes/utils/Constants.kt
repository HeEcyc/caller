package com.roobcall.themes.utils

import com.roobcall.themes.App
import com.roobcall.themes.model.theme.ImageTheme
import com.roobcall.themes.model.theme.Theme
import com.roobcall.themes.model.theme.VideoTheme

val videoThemes = List(6) {
    VideoTheme(
        "file:///android_asset/top/video/$it.mp4",
        "file:///android_asset/top/gif/$it.gif",
        true
    )
}
val audioThemesIndexes = videoThemes.indices.toList()

val themesNew: List<Theme> = MutableList<Theme>(8) {
    ImageTheme("file:///android_asset/new/$it.png")
}
val themesTop: List<Theme> = MutableList<Theme>(8) {
    ImageTheme("file:///android_asset/top/image/$it.png")
}.apply { addAll(0, videoThemes) }.toList()
val themesPopular = List(8) {
    ImageTheme("file:///android_asset/popular/$it.png")
}
val presetThemes: List<Theme> = listOf(
    *themesTop.toTypedArray(),
    *themesNew.toTypedArray(),
    *themesPopular.toTypedArray()
)

val appLink: String by lazy { "https://play.google.com/store/apps/details?id=" + App.instance.packageName }

const val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ#"