package com.stacky.caller.ui.preview

import com.stacky.caller.R
import com.stacky.caller.base.BaseDialog
import com.stacky.caller.databinding.AppliedDialogBinding
import com.stacky.caller.utils.setOnClickListener

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        binding.buttonOk.setOnClickListener(::dismiss)
        binding.buttonCancel.setOnClickListener(::dismiss)
        binding.buttonClose.setOnClickListener(::dismiss)
    }

}