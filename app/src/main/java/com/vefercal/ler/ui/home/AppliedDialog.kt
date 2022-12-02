package com.vefercal.ler.ui.home

import com.vefercal.ler.R
import com.vefercal.ler.base.BaseDialog
import com.vefercal.ler.databinding.AppliedDialogBinding
import com.vefercal.ler.utils.setOnClickListener

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        binding.buttonOk.setOnClickListener(::dismiss)
        binding.buttonClose.setOnClickListener(::dismiss)
    }

}