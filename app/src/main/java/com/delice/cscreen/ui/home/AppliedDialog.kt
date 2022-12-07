package com.delice.cscreen.ui.home

import com.delice.cscreen.R
import com.delice.cscreen.base.BaseDialog
import com.delice.cscreen.databinding.AppliedDialogBinding
import com.delice.cscreen.utils.setOnClickListener

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        binding.buttonOk.setOnClickListener(::dismiss)
        binding.buttonClose.setOnClickListener(::dismiss)
    }

}