package com.vefercal.ler.ui.dial.fragment

import android.graphics.Color
import androidx.databinding.ObservableField
import com.vefercal.ler.R
import com.vefercal.ler.base.BaseViewModel
import com.vefercal.ler.repository.PermissionRepository

class DialViewModel(
    val permissionRepository: PermissionRepository
) : BaseViewModel() {

    val text = ObservableField("")
    val adapter = DialAdapter(
        ::onButtonClick,
        R.drawable.dial_button_dial_fragment,
        Color.parseColor("#291C33")
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