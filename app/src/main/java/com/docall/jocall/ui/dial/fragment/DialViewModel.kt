package com.docall.jocall.ui.dial.fragment

import androidx.databinding.ObservableField
import com.docall.jocall.base.BaseViewModel
import com.docall.jocall.repository.PermissionRepository

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