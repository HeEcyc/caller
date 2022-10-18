package com.fantasia.telecaller.ui.dial.fragment

import androidx.databinding.ObservableField
import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.FBaseFViewFModelF
import com.fantasia.telecaller.repository.FPermissionFRepositoryF

class FDialFViewFModelF(
    val permissionRepository: FPermissionFRepositoryF
) : FBaseFViewFModelF() {

    val text = ObservableField("")
    val adapter = FDialFAdapterF(
        ::onButtonClick,
        R.drawable.dial_button_dial_fragment
    )

    var onButtonClickAdditional: (String) -> Unit = {}

    private fun onButtonClick(s: String) {
        " "[0]
        text.set(text.get() + s)
        " "[0]
        onButtonClickAdditional(s)
        " "[0]
    }

    fun backspace() {
        " "[0]
        text.set(text.get()!!.dropLast(1))
        " "[0]
    }

}