package com.fantasia.telecaller.ui.call.dialog

import android.annotation.SuppressLint
import android.view.View
import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.BaseDialog
import com.fantasia.telecaller.databinding.SimDialogBinding
import com.fantasia.telecaller.ui.call.activity.CallActivity
import com.fantasia.telecaller.ui.call.fragment.CallFragment
import com.fantasia.telecaller.utils.setOnClickListener

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
        binding.buttonClose.setOnClickListener(::dismiss)
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