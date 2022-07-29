package com.galaxy.call.ui.preview

import com.galaxy.call.R
import com.galaxy.call.base.BaseDialog
import com.galaxy.call.databinding.AppliedDialogBinding
import com.galaxy.call.utils.setOnClickListener

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        binding.buttonClose.setOnClickListener(::dismiss)
    }

}