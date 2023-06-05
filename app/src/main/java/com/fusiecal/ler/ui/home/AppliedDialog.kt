package com.fusiecal.ler.ui.home

import com.fusiecal.ler.R
import com.fusiecal.ler.base.BaseDialog
import com.fusiecal.ler.databinding.AppliedDialogBinding
import com.fusiecal.ler.utils.setOnClickListener

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        binding.buttonClose.setOnClickListener(::dismiss)
    }

}