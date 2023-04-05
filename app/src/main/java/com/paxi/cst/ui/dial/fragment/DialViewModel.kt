package com.paxi.cst.ui.dial.fragment

import androidx.databinding.ObservableField
import com.paxi.cst.base.BaseViewModel
import com.paxi.cst.repository.PermissionRepository

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