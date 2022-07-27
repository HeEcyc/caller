package com.yellowmood.caller.ui.preview

import com.yellowmood.caller.R
import com.yellowmood.caller.base.BaseDialog
import com.yellowmood.caller.databinding.AppliedDialogBinding
import com.yellowmood.caller.utils.setOnClickListener

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        binding.buttonClose.setOnClickListener(::dismiss)
    }

}