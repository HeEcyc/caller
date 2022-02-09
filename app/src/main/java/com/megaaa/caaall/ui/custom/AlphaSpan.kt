package com.megaaa.caaall.ui.custom

import android.text.TextPaint
import android.text.style.CharacterStyle

class AlphaSpan : CharacterStyle() {

    override fun updateDrawState(tp: TextPaint) {
        tp.alpha = (255 * 0.5).toInt()
    }

}