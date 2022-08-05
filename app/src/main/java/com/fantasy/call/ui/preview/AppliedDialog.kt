package com.fantasy.call.ui.preview

import com.fantasy.call.R
import com.fantasy.call.base.BaseDialog
import com.fantasy.call.databinding.AppliedDialogBinding
import com.fantasy.call.utils.setOnClickListener

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        binding.buttonClose.setOnClickListener(::dismiss)
    }

}