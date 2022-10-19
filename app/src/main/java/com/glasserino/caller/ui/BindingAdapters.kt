package com.glasserino.caller.ui

import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("font")
fun setFontRes(tv: TextView, r: Int) {
    tv.typeface = ResourcesCompat.getFont(tv.context, r)
}

@BindingAdapter("visibleIf")
fun View.visibleIf(b: Boolean) {
    visibility = if (b) View.VISIBLE else View.GONE
}

@BindingAdapter("goneIf")
fun View.goneIf(b: Boolean) {
    visibility = if (b) View.GONE else View.VISIBLE
}