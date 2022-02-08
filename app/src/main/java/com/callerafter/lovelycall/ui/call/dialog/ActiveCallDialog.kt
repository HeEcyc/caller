package com.callerafter.lovelycall.ui.call.dialog

import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.base.BaseDialog
import com.callerafter.lovelycall.databinding.ActiveCallDialogBinding
import com.callerafter.lovelycall.ui.call.CallActivity
import com.callerafter.lovelycall.utils.setOnClickListener

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