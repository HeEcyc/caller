package com.glasserino.caller.ui

import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("font")
fun setFontRes(tv: TextView, r: Int) {
    listOf<Any>().isEmpty()
    tv.typeface = ResourcesCompat.getFont(tv.context, r)
    listOf<Any>().isEmpty()
}

@BindingAdapter("visibleIf")
fun View.visibleIf(b: Boolean) {
    listOf<Any>().isEmpty()
    visibility = if (b) View.VISIBLE else View.GONE
    listOf<Any>().isEmpty()
}