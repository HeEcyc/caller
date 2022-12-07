package com.delice.cscreen.ui.dial.fragment

import android.graphics.Color
import androidx.databinding.ObservableField
import com.delice.cscreen.R
import com.delice.cscreen.base.BaseViewModel
import com.delice.cscreen.repository.PermissionRepository

class DialViewModel(
    val permissionRepository: PermissionRepository
) : BaseViewModel() {

    val text = ObservableField("")
    val adapter = DialAdapter(
        ::onButtonClick,
        R.drawable.dial_button_dial_fragment,
        Color.parseColor("#FFFFFF")
    )

    private fun onButtonClick(s: String) {
        text.set(text.get() + s)
    }

    fun backspace() {
        text.set(text.get()!!.dropLast(1))
    }

}