package com.yee.zer.ui.call.dialog

import android.annotation.SuppressLint
import com.yee.zer.R
import com.yee.zer.base.BaseDialog
import com.yee.zer.databinding.SimDialogBinding
import com.yee.zer.ui.call.activity.CallActivity
import com.yee.zer.ui.call.fragment.CallFragment
import com.yee.zer.utils.setOnClickListener

class SimDialog : BaseDialog<SimDialogBinding>(R.layout.sim_dialog) {

    private var selected = 0

    @SuppressLint("MissingPermission")
    override fun setupUI() {
        val simCards = activityAs<CallActivity>().viewModel.callRepository.subscriptionManager.activeSubscriptionInfoList.map { it.displayName }
        binding.buttonSim1.setOnClickListener {
            selected = 0
            binding.buttonSim1.alpha = 1f
            binding.buttonSim2.alpha = 0.5f
        }
        binding.buttonSim2.setOnClickListener {
            selected = 1
            binding.buttonSim2.alpha = 1f
            binding.buttonSim1.alpha = 0.5f
        }
        binding.buttonOk.setOnClickListener {
            if (simCards.getOrNull(selected) !== null) onSIMSelected(selected)
        }
        binding.buttonCancel.setOnClickListener(::dismiss)
        binding.buttonClose.setOnClickListener(::dismiss)
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