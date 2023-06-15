package com.docall.jocall.utils

import com.docall.jocall.model.theme.Theme
import com.docall.jocall.model.theme.VideoTheme

val videoThemes = List(5) {
    VideoTheme(
        "file:///android_asset/videos/vid_$it.mp4",
        "file:///android_asset/gifs/$it.gif",
        true
    )
}
val presetThemes: List<Theme> = listOf(
    *videoThemes.toTypedArray()
)