package com.glass.call.utils

import com.glass.call.App
import com.glass.call.model.theme.ImageTheme
import com.glass.call.model.theme.Theme
import com.glass.call.model.theme.VideoTheme

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