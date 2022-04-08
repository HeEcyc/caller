package com.holographic.call.ui.main

import com.holographic.call.R
import com.holographic.call.base.BaseDialog
import com.holographic.call.databinding.GuidDialogBinding
import com.holographic.call.utils.setOnClickListener

class GuidDialog : BaseDialog<GuidDialogBinding>(R.layout.guid_dialog) {
    override fun setupUI() {
        binding.buttonOk.setOnClickListener(::dismiss)
    }
}