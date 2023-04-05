package com.paxi.cst.utils

import com.paxi.cst.model.theme.Theme
import com.paxi.cst.model.theme.VideoTheme

val videoThemes = List(4) {
    VideoTheme(
        "file:///android_asset/videos/vid_$it.mp4",
        "file:///android_asset/gifs/$it.gif",
        true
    )
}
val presetThemes: List<Theme> = listOf(
    *videoThemes.toTypedArray()
)