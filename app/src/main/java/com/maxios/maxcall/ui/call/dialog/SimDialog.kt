package com.maxios.maxcall.ui.call.dialog

import android.annotation.SuppressLint
import com.maxios.maxcall.R
import com.maxios.maxcall.base.BaseDialog
import com.maxios.maxcall.databinding.SimDialogBinding
import com.maxios.maxcall.ui.call.CallActivity
import com.maxios.maxcall.ui.call.fragment.CallFragment

class SimDialog : BaseDialog<SimDialogBinding>(R.layout.sim_dialog) {

    @SuppressLint("MissingPermission")
    override fun setupUI() {
        val simCards = activityAs<CallActivity>().viewModel.callRepository.subscriptionManager.activeSubscriptionInfoList.map { it.displayName }
        binding.buttonSIM1.setOnClickListener { if (simCards.getOrNull(0) !== null) onSIMSelected(0) }
        binding.buttonSIM2.setOnClickListener { if (simCards.getOrNull(1) !== null) onSIMSelected(1) }
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