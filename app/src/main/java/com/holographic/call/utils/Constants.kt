package com.holographic.call.utils

import com.holographic.call.App
import com.holographic.call.model.theme.ImageTheme
import com.holographic.call.model.theme.Theme
import com.holographic.call.model.theme.VideoTheme

val defaultTheme = ImageTheme("file:///android_asset/theme_placeholder.webp")
val audioThemesIndexes = List(6) { it }
val themesPopular: List<Theme> = List(6) {
    VideoTheme(
        "file:///android_asset/popular/videos/$it.mp4",
        "file:///android_asset/popular/gifs/$it.gif",
        it in audioThemesIndexes
    )
}
val themesGames: List<Theme> = List(6) {
    ImageTheme("file:///android_asset/games/$it.webp")
}
val themesCats: List<Theme> = List(6) {
    ImageTheme("file:///android_asset/cats/$it.webp")
}
val themesMovies: List<Theme> = List(6) {
    ImageTheme("file:///android_asset/movies/$it.webp")
}
val presetThemes: List<Theme> = listOf(
    *themesPopular.toTypedArray(),
    *themesGames.toTypedArray(),
    *themesCats.toTypedArray(),
    *themesMovies.toTypedArray()
)
val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#".toList()

val appLink: String by lazy { "https://play.google.com/store/apps/details?id=" + App.instance.packageName }

const val IRON_SOURCE_APP_KEY = "144a03a51"