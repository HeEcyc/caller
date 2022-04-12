package com.maxios.maxcall.utils

import com.maxios.maxcall.App
import com.maxios.maxcall.model.theme.ImageTheme
import com.maxios.maxcall.model.theme.Theme
import com.maxios.maxcall.model.theme.VideoTheme

val defaultTheme = ImageTheme("file:///android_asset/theme_placeholder.png")
val audioThemesIndexes = listOf<Int>()
val themesPopular: List<Theme> = List(0) {
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
val themesMovies: List<Theme> = List(5) {
    ImageTheme("file:///android_asset/movies/$it.webp")
}
val presetThemes: List<Theme> = listOf(
    *themesPopular.toTypedArray(),
    *themesGames.toTypedArray(),
    *themesCats.toTypedArray(),
    *themesMovies.toTypedArray()
)

val appLink: String by lazy { "https://play.google.com/store/apps/details?id=" + App.instance.packageName }

const val IRON_SOURCE_API_KEY = "145c7bfe9"