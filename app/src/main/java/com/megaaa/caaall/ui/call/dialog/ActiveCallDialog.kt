package com.megaaa.caaall.ui.call.dialog

import com.megaaa.caaall.R
import com.megaaa.caaall.base.BaseDialog
import com.megaaa.caaall.databinding.ActiveCallDialogBinding
import com.megaaa.caaall.ui.call.CallActivity
import com.megaaa.caaall.utils.setOnClickListener

class ActiveCallDialog : BaseDialog<ActiveCallDialogBinding>(R.layout.active_call_dialog) {

    override fun setupUI() {
        val items = activityAs<CallActivity>().getActiveCallViewModelItems()
        val adapter = ActiveCallAdapter {
            activityAs<CallActivity>().swapCallsToContact(it.contact)
            dismiss()
        }
        adapter.reloadData(items)
        binding.shadowRecycler.adapter = adapter
        binding.recycler.adapter = adapter
        binding.buttonCancel.setOnClickListener(::dismiss)
    }

}