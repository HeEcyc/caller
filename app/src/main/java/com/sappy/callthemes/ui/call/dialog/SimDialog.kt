package com.sappy.callthemes.ui.call.dialog

import android.annotation.SuppressLint
import android.view.View
import com.sappy.callthemes.R
import com.sappy.callthemes.base.BaseDialog
import com.sappy.callthemes.databinding.SimDialogBinding
import com.sappy.callthemes.ui.call.activity.CallActivity
import com.sappy.callthemes.ui.call.fragment.CallFragment
import com.sappy.callthemes.utils.setOnClickListener

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
        binding.buttonOk.setOnClickListener {
            if (simCards.getOrNull(selected) !== null) onSIMSelected(selected)
        }
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