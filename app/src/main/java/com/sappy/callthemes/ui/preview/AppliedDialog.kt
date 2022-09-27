package com.sappy.callthemes.ui.preview

import com.sappy.callthemes.R
import com.sappy.callthemes.base.BaseDialog
import com.sappy.callthemes.databinding.AppliedDialogBinding
import com.sappy.callthemes.utils.setOnClickListener

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        binding.buttonOk.setOnClickListener(::dismiss)
    }

}