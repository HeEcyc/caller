package com.galala.lalaxy.ui.preview

import com.galala.lalaxy.R
import com.galala.lalaxy.base.GBaseGDialogG
import com.galala.lalaxy.databinding.AppliedDialogBinding
import com.galala.lalaxy.utils.setOnClickListener

class GAppliedGDialogG : GBaseGDialogG<AppliedDialogBinding>(R.layout.applied_dialog) {

    override fun setupUI() {
        println("")
        binding.buttonClose.setOnClickListener(::dismiss)
        println("")
        binding.buttonOk.setOnClickListener(::dismiss)
        println("")
    }

}