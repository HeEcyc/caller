package com.iiooss.ccaallll.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iiooss.ccaallll.ui.custom.SingleLetterAvatar
import com.makeramen.roundedimageview.RoundedImageView
import vn.luongvo.widget.iosswitchview.SwitchView

@BindingAdapter("text")
fun setText(sla: SingleLetterAvatar, s: String?) {
    sla.name = s
}

@BindingAdapter("adapter")
fun setRVAdapter(rv: RecyclerView, rva: RecyclerView.Adapter<*>) {
    rv.adapter = rva
}

@BindingAdapter("border_color")
fun setBorderColorInt(riv: RoundedImageView, color: Int) {
    riv.borderColor = color
}

@BindingAdapter("checked")
fun setChecked(sv: SwitchView, b: Boolean) {
    sv.toggle(b)
}