package com.threed.caller.ui.preview

import com.threed.caller.R
import com.threed.caller.base.BaseDialog
import com.threed.caller.databinding.AppliedDialogBinding
import com.threed.caller.utils.setOnClickListener

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        binding.buttonOk.setOnClickListener(::dismiss)
    }

}