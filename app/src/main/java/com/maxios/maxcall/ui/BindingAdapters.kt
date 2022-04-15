package com.maxios.maxcall.ui

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("backgroundColorInt")
fun setBackgroundColorInt(v: View, c: Int) {
    v.setBackgroundColor(c)
}