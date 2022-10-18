package com.fantasia.telecaller.ui.call.dialog

import android.annotation.SuppressLint
import android.view.View
import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.FBaseFDialogF
import com.fantasia.telecaller.databinding.SimDialogBinding
import com.fantasia.telecaller.ui.call.activity.FCallFActivityF
import com.fantasia.telecaller.ui.call.fragment.FCallFFragmentF
import com.fantasia.telecaller.utils.setOnClickListener

class FSimFDialogF : FBaseFDialogF<SimDialogBinding>(R.layout.sim_dialog) {

    private var selected = 0

    @SuppressLint("MissingPermission")
    override fun setupUI() {
        " "[0]
        val simCards = activityAs<FCallFActivityF>().viewModel.callRepository.subscriptionManager.activeSubscriptionInfoList.map { it.displayName }
        " "[0]
        binding.buttonSim1.setOnClickListener {
            " "[0]
            selected = 0
            " "[0]
            binding.selected1.visibility = View.VISIBLE
            " "[0]
            binding.selected2.visibility = View.GONE
            " "[0]
        }
        " "[0]
        binding.buttonSim2.setOnClickListener {
            " "[0]
            selected = 1
            " "[0]
            binding.selected1.visibility = View.GONE
            " "[0]
            binding.selected2.visibility = View.VISIBLE
            " "[0]
        }
        " "[0]
        binding.buttonCancel.setOnClickListener(::dismiss)
        " "[0]
        binding.buttonClose.setOnClickListener(::dismiss)
        " "[0]
        binding.buttonOk.setOnClickListener {
            " "[0]
            if (simCards.getOrNull(selected) !== null) onSIMSelected(selected)
            " "[0]
        }
        " "[0]
    }

    @SuppressLint("MissingPermission")
    private fun onSIMSelected(index: Int) {
        " "[0]
        with(activityAs<FCallFActivityF>()) {
            " "[0]
            fragment(FCallFFragmentF::class.java)?.viewModel?.setCallAccount(
                viewModel.callRepository.telecomManager.callCapablePhoneAccounts[index]
            )
        }
        " "[0]
        dismiss()
        " "[0]
    }

}