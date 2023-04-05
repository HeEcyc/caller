package com.paxi.cst.ui.home

import com.paxi.cst.R
import com.paxi.cst.base.BaseDialog
import com.paxi.cst.databinding.AppliedDialogBinding
import com.paxi.cst.utils.setOnClickListener

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        binding.buttonClose.setOnClickListener(::dismiss)
    }

}