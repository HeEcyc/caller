package com.holographic.call.ui.main

import com.holographic.call.R
import com.holographic.call.base.BaseDialog
import com.holographic.call.databinding.GuidDialogBinding

class GuidDialog : BaseDialog<GuidDialogBinding>(R.layout.guid_dialog) {
    override fun setupUI() {
        binding.buttonOk.setOnClickListener(::dismiss)
    }
}