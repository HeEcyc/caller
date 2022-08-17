package com.fancy.call.ui.preview

import com.fancy.call.R
import com.fancy.call.base.BaseDialog
import com.fancy.call.databinding.AppliedDialogBinding
import com.fancy.call.utils.setOnClickListener

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        binding.buttonClose.setOnClickListener(::dismiss)
        binding.buttonOk.setOnClickListener(::dismiss)
    }

}