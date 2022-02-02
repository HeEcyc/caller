package com.callerafter.lovelycall.ui.theme

import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.base.BaseDialog
import com.callerafter.lovelycall.databinding.SuccessDialogBinding
import com.callerafter.lovelycall.utils.setOnClickListener

class SuccessDialog : BaseDialog<SuccessDialogBinding>(R.layout.success_dialog) {

    override fun setupUI() {
        binding.buttonClose.setOnClickListener(::dismiss)
        Handler(Looper.getMainLooper()).postDelayed(3000) { dismiss() }
    }

}