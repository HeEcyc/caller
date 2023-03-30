package com.bbbotttixxx.callscreen.ui.home

import com.bbbotttixxx.callscreen.R
import com.bbbotttixxx.callscreen.base.BaseDialog
import com.bbbotttixxx.callscreen.databinding.AppliedDialogBinding
import com.bbbotttixxx.callscreen.utils.setOnClickListener

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        binding.buttonOk.setOnClickListener(::dismiss)
        binding.buttonClose.setOnClickListener(::dismiss)
        binding.buttonCancel.setOnClickListener(::dismiss)
    }

}