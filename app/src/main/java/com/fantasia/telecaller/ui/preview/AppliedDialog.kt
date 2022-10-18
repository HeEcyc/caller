package com.fantasia.telecaller.ui.preview

import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.BaseDialog
import com.fantasia.telecaller.databinding.AppliedDialogBinding
import com.fantasia.telecaller.utils.setOnClickListener

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        binding.buttonClose.setOnClickListener(::dismiss)
    }

}