package com.fancy.call.ui.call.dialog

import android.annotation.SuppressLint
import android.view.View
import com.fancy.call.R
import com.fancy.call.base.BaseDialog
import com.fancy.call.databinding.SimDialogBinding
import com.fancy.call.ui.call.activity.CallActivity
import com.fancy.call.ui.call.fragment.CallFragment
import com.fancy.call.utils.setOnClickListener

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
        binding.buttonOk.setOnClickListener {
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