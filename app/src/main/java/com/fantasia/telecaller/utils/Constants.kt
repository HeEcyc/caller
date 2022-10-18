package com.fantasia.telecaller.utils

import com.fantasia.telecaller.FAppF
import com.fantasia.telecaller.model.theme.ImageTheme
import com.fantasia.telecaller.model.theme.Theme
import com.fantasia.telecaller.model.theme.VideoTheme

val videoThemes = List(8) {
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
val themesCute: List<Theme> = List(8) {
    ImageTheme("file:///android_asset/cute/$it.jpg")
}
val themesRecommended = List(8) {
    ImageTheme("file:///android_asset/recommended/$it.jpg")
}
val presetThemes: List<Theme> = listOf(
    *themesTop.toTypedArray(),
    *themesCute.toTypedArray(),
    *themesRecommended.toTypedArray()
)

val appLink: String by lazy { "https://play.google.com/store/apps/details?id=" + FAppF.instance.packageName }

const val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ#"