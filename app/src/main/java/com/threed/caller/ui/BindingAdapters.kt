package com.threed.caller.ui

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleIf")
fun View.visibleIf(b: Boolean) {
    visibility = if (b) View.VISIBLE else View.GONE
}

@BindingAdapter("goneIf")
fun View.goneIf(b: Boolean) {
    visibility = if (b) View.GONE else View.VISIBLE
}