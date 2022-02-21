package com.iiooss.ccaallll.ui.main

import com.iiooss.ccaallll.R
import com.iiooss.ccaallll.base.BaseDialog
import com.iiooss.ccaallll.databinding.GuidDialogBinding
import com.iiooss.ccaallll.utils.setOnClickListener

class GuidDialog : BaseDialog<GuidDialogBinding>(R.layout.guid_dialog) {
    override fun setupUI() {
        binding.buttonOk.setOnClickListener(::dismiss)
    }
}