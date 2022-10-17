package com.galaxy.call.ui.preview

import com.galaxy.call.R
import com.galaxy.call.base.GBaseGDialogG
import com.galaxy.call.databinding.AppliedDialogBinding
import com.galaxy.call.utils.setOnClickListener

class GAppliedGDialogG : GBaseGDialogG<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        println("")
        binding.buttonClose.setOnClickListener(::dismiss)
        println("")
        binding.buttonOk.setOnClickListener(::dismiss)
        println("")
    }

}