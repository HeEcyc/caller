package com.roobcall.themes.ui.dial.fragment

import android.graphics.Color
import androidx.databinding.ObservableField
import com.roobcall.themes.R
import com.roobcall.themes.base.BaseViewModel
import com.roobcall.themes.repository.PermissionRepository

class DialViewModel(
    val permissionRepository: PermissionRepository
) : BaseViewModel() {

    val text = ObservableField("")
    val adapter = DialAdapter(
        ::onButtonClick,
        R.drawable.dial_button_dial_fragment,
        Color.parseColor("#FFFFFF")
    )

    var onButtonClickAdditional: (String) -> Unit = {}

    private fun onButtonClick(s: String) {
        text.set(text.get() + s)
        onButtonClickAdditional(s)
    }

    fun backspace() {
        text.set(text.get()!!.dropLast(1))
    }

}