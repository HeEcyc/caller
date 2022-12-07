package com.delice.cscreen.utils

import com.delice.cscreen.App
import com.delice.cscreen.model.theme.Theme
import com.delice.cscreen.model.theme.VideoTheme

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