package com.vefercal.ler.utils

import com.vefercal.ler.App
import com.vefercal.ler.model.theme.Theme
import com.vefercal.ler.model.theme.VideoTheme

val videoThemes = List(8) {
    VideoTheme(
        "file:///android_asset/videos/vid_${it + 1}.mp4",
        "file:///android_asset/gifs/$it.gif",
        true
    )
}
val presetThemes: List<Theme> = listOf(
    *videoThemes.toTypedArray()
)

val appLink: String by lazy { "https://play.google.com/store/apps/details?id=" + App.instance.packageName }