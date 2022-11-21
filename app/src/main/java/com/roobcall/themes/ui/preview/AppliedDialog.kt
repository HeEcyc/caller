package com.roobcall.themes.ui.preview

import com.roobcall.themes.R
import com.roobcall.themes.base.BaseDialog
import com.roobcall.themes.databinding.AppliedDialogBinding
import com.roobcall.themes.utils.setOnClickListener

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        binding.buttonOk.setOnClickListener(::dismiss)
        binding.buttonClose.setOnClickListener(::dismiss)
    }

}