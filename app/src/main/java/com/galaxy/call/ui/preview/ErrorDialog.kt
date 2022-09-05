package com.galaxy.call.ui.preview

import androidx.core.os.bundleOf
import com.galaxy.call.R
import com.galaxy.call.base.BaseDialog
import com.galaxy.call.databinding.AlertDialogBinding
import com.galaxy.call.ui.main.PermissionDialog
import com.galaxy.call.utils.setOnClickListener

class ErrorDialog : BaseDialog<AlertDialogBinding>(R.layout.alert_dialog) {

    companion object {
        fun newInstance(requestKey: String) = ErrorDialog().apply {
            arguments = bundleOf("request_key" to requestKey)
        }
    }

    override fun setupUI() {
        binding.buttonClose.setOnClickListener(::dismiss)
        binding.buttonOk.setOnClickListener {
            PermissionDialog.newInstance(requireArguments().getString("request_key")!!)
                .show(parentFragmentManager)
            dismiss()
        }
    }

}