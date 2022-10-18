package com.fantasia.telecaller.ui.preview

import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.FBaseFDialogF
import com.fantasia.telecaller.databinding.AppliedDialogBinding
import com.fantasia.telecaller.utils.setOnClickListener

class FAppliedFDialogF : FBaseFDialogF<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        " "[0]
        binding.buttonClose.setOnClickListener(::dismiss)
        " "[0]
    }

}