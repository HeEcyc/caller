package com.callerafter.lovelycall.ui.call.dialog

import android.annotation.SuppressLint
import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.base.BaseDialog
import com.callerafter.lovelycall.databinding.SimDialogBinding
import com.callerafter.lovelycall.ui.call.CallActivity
import com.callerafter.lovelycall.ui.call.fragment.CallFragment
import com.callerafter.lovelycall.utils.setOnClickListener

class SimDialog : BaseDialog<SimDialogBinding>(R.layout.sim_dialog) {

    @SuppressLint("MissingPermission")
    override fun setupUI() {
        val simCards = activityAs<CallActivity>().viewModel.callRepository.subscriptionManager.activeSubscriptionInfoList.map { it.displayName }
        binding.nameSIM1.text = simCards.getOrNull(0)
        binding.nameSIM2.text = simCards.getOrNull(1)
        binding.buttonCancel.setOnClickListener(::dismiss)
        binding.buttonSIM1.setOnClickListener { if (binding.nameSIM1.text !== null) onSIMSelected(0) }
        binding.buttonSIM2.setOnClickListener { if (binding.nameSIM2.text !== null) onSIMSelected(1) }
    }

    @SuppressLint("MissingPermission")
    private fun onSIMSelected(index: Int) {
        with(activityAs<CallActivity>()) {
            fragment(CallFragment::class.java)?.viewModel?.setCallAccount(
                viewModel.callRepository.telecomManager.callCapablePhoneAccounts[index]
            )
        }
        dismiss()
    }

}