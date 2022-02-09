package com.megaaa.caaall.ui.call.dialog

import android.annotation.SuppressLint
import com.megaaa.caaall.R
import com.megaaa.caaall.base.BaseDialog
import com.megaaa.caaall.databinding.SimDialogBinding
import com.megaaa.caaall.ui.call.CallActivity
import com.megaaa.caaall.ui.call.fragment.CallFragment
import com.megaaa.caaall.utils.setOnClickListener

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