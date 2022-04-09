package com.holographic.call.ui

import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.holographic.call.ui.custom.SingleLetterAvatar
import com.makeramen.roundedimageview.RoundedImageView
import vn.luongvo.widget.iosswitchview.SwitchView

@BindingAdapter("text")
fun setText(sla: SingleLetterAvatar, s: String?) {
    sla.name = s
}

@BindingAdapter("adapter")
fun setRVAdapter(rv: RecyclerView, rva: RecyclerView.Adapter<*>?) {
    rva ?: return
    rv.adapter = rva
}

@BindingAdapter("border_color")
fun setBorderColorInt(riv: RoundedImageView, color: Int) {
    riv.borderColor = color
}

@BindingAdapter("tintInt")
fun setTintInt(iv: ImageView, c: Int) {
    if (c == -1) {
        iv.imageTintList = null
        return
    }
    iv.imageTintList = ColorStateList.valueOf(c)
}

@BindingAdapter("checked")
fun setChecked(sv: SwitchView, b: Boolean) {
    sv.toggle(b)
}