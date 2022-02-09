package com.megaaa.caaall.ui.theme

import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import com.megaaa.caaall.R
import com.megaaa.caaall.base.BaseDialog
import com.megaaa.caaall.databinding.SuccessDialogBinding
import com.megaaa.caaall.utils.setOnClickListener

class SuccessDialog : BaseDialog<SuccessDialogBinding>(R.layout.success_dialog) {

    override fun setupUI() {
        binding.buttonClose.setOnClickListener(::dismiss)
        Handler(Looper.getMainLooper()).postDelayed(3000) { dismiss() }
    }

}