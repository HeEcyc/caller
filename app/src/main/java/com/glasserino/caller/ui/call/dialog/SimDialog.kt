package com.glasserino.caller.ui.call.dialog

import android.annotation.SuppressLint
import android.view.View
import com.glasserino.caller.R
import com.glasserino.caller.base.BaseDialog
import com.glasserino.caller.databinding.SimDialogBinding
import com.glasserino.caller.ui.call.CallActivity
import com.glasserino.caller.ui.call.fragment.CallFragment
import com.glasserino.caller.utils.setOnClickListener

class SimDialog : BaseDialog<SimDialogBinding>(R.layout.sim_dialog) {

    private var selected = 0

    @SuppressLint("MissingPermission")
    override fun setupUI() {
        val simCards = activityAs<CallActivity>().viewModel.callRepository.subscriptionManager.activeSubscriptionInfoList.map { it.displayName }
        binding.buttonSim1.setOnClickListener {
            selected = 0
            binding.selected1.visibility = View.VISIBLE
            binding.selected2.visibility = View.GONE
        }
        binding.buttonSim2.setOnClickListener {
            selected = 1
            binding.selected1.visibility = View.GONE
            binding.selected2.visibility = View.VISIBLE
        }
        binding.buttonCancel.setOnClickListener(::dismiss)
        binding.buttonSelect.setOnClickListener {
            if (simCards.getOrNull(selected) !== null) onSIMSelected(selected)
        }
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