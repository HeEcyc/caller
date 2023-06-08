package com.yee.zer.ui.home

import com.yee.zer.R
import com.yee.zer.base.BaseDialog
import com.yee.zer.databinding.AppliedDialogBinding
import com.yee.zer.utils.setOnClickListener

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        binding.buttonClose.setOnClickListener(::dismiss)
    }

}