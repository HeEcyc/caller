package com.galala.lalaxy.utils

import com.galala.lalaxy.GAppG
import com.galala.lalaxy.model.theme.ImageTheme
import com.galala.lalaxy.model.theme.Theme
import com.galala.lalaxy.model.theme.VideoTheme

val videoThemes = List(8) {
    VideoTheme(
        "file:///android_asset/new/video/$it.mp4",
        "file:///android_asset/new/gif/$it.gif",
        true
    )
}
val themesTop = List(8) {
    ImageTheme("file:///android_asset/top/$it.jpeg")
}
val themesNew = MutableList<Theme>(8) {
    ImageTheme("file:///android_asset/new/image/$it.jpeg")
}.apply { addAll(0, videoThemes) }.toList()
val themesPopular: List<Theme> = List(8) {
    ImageTheme("file:///android_asset/popular/$it.jpeg")
}
val presetThemes: List<Theme> = listOf(
    *themesNew.toTypedArray(),
    *themesTop.toTypedArray(),
    *themesPopular.toTypedArray()
)

val appLink: String by lazy { "https://play.google.com/store/apps/details?id=" + GAppG.instance.packageName }

const val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ#"