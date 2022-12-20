package com.pecall.poscreen.ui.home

import com.pecall.poscreen.R
import com.pecall.poscreen.base.BaseDialog
import com.pecall.poscreen.databinding.AppliedDialogBinding
import com.pecall.poscreen.utils.setOnClickListener

class AppliedDialog : BaseDialog<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        binding.buttonOk.setOnClickListener(::dismiss)
    }

}