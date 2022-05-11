package com.glass.call.ui

import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("font")
fun setFontRes(tv: TextView, r: Int) {
    tv.typeface = ResourcesCompat.getFont(tv.context, r)
}