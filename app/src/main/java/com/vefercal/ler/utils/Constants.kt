package com.vefercal.ler.utils

import com.vefercal.ler.App
import com.vefercal.ler.model.theme.ImageTheme
import com.vefercal.ler.model.theme.Theme
import com.vefercal.ler.model.theme.VideoTheme

val videoThemes = List(6) {
    VideoTheme(
        "file:///android_asset/popular/video/$it.mp4",
        "file:///android_asset/popular/gif/$it.gif",
        true
    )
}
val themesNew = List(8) {
    ImageTheme("file:///android_asset/new/$it.png")
}
val themesTop = List(8) {
    ImageTheme("file:///android_asset/top/$it.png")
}
val themesPopular: List<Theme> = MutableList<Theme>(8) {
    ImageTheme("file:///android_asset/popular/image/$it.png")
}.apply { addAll(0, videoThemes) }.toList()
val presetThemes: List<Theme> = listOf(
    *themesPopular.toTypedArray(),
    *themesTop.toTypedArray(),
    *themesNew.toTypedArray()
)

val appLink: String by lazy { "https://play.google.com/store/apps/details?id=" + App.instance.packageName }