package com.delice.cscreen.ui

import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleIf")
fun View.visibleIf(b: Boolean) {
    visibility = if (b) View.VISIBLE else View.GONE
}

@BindingAdapter("font")
fun TextView.font(res: Int) {
    typeface = ResourcesCompat.getFont(context, res)
}

@BindingAdapter("textColorInt")
fun TextView.setTextColorInt(c: Int) {
    setTextColor(ColorStateList.valueOf(c))
}