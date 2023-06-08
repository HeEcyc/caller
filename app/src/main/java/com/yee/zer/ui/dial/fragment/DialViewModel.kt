package com.yee.zer.ui.dial.fragment

import androidx.databinding.ObservableField
import com.yee.zer.base.BaseViewModel
import com.yee.zer.repository.PermissionRepository

class DialViewModel(
    val permissionRepository: PermissionRepository
) : BaseViewModel() {

    val text = ObservableField("")
    val adapter = DialAdapter(
        ::onButtonClick
    )

    private fun onButtonClick(s: String) {
        text.set(text.get() + s)
    }

    fun backspace() {
        text.set(text.get()!!.dropLast(1))
    }

}