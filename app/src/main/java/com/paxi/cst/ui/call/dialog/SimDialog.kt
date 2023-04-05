package com.paxi.cst.ui.call.dialog

import android.annotation.SuppressLint
import android.view.View
import com.paxi.cst.R
import com.paxi.cst.base.BaseDialog
import com.paxi.cst.databinding.SimDialogBinding
import com.paxi.cst.ui.call.activity.CallActivity
import com.paxi.cst.ui.call.fragment.CallFragment
import com.paxi.cst.utils.setOnClickListener

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