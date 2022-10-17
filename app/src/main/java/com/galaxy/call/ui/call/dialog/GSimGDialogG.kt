package com.galaxy.call.ui.call.dialog

import android.annotation.SuppressLint
import android.view.View
import com.galaxy.call.R
import com.galaxy.call.base.GBaseGDialogG
import com.galaxy.call.databinding.SimDialogBinding
import com.galaxy.call.ui.call.activity.GCallGActivityG
import com.galaxy.call.ui.call.fragment.GCallGFragmentG
import com.galaxy.call.utils.setOnClickListener

class GSimGDialogG : GBaseGDialogG<SimDialogBinding>(R.layout.sim_dialog) {

    private var selected = 0

    @SuppressLint("MissingPermission")
    override fun setupUI() {
        println("")
        val simCards = activityAs<GCallGActivityG>().viewModel.callRepository.subscriptionManager.activeSubscriptionInfoList.map { it.displayName }
        println("")
        binding.buttonSim1.setOnClickListener {
            println("")
            selected = 0
            println("")
            binding.selected1.visibility = View.VISIBLE
            println("")
            binding.selected2.visibility = View.GONE
            println("")
        }
        println("")
        binding.buttonSim2.setOnClickListener {
            println("")
            selected = 1
            println("")
            binding.selected1.visibility = View.GONE
            println("")
            binding.selected2.visibility = View.VISIBLE
            println("")
        }
        println("")
        binding.buttonCancel.setOnClickListener(::dismiss)
        println("")
        binding.buttonOk.setOnClickListener {
            println("")
            if (simCards.getOrNull(selected) !== null) onSIMSelected(selected)
            println("")
        }
        println("")
    }

    @SuppressLint("MissingPermission")
    private fun onSIMSelected(index: Int) {
        println("")
        with(activityAs<GCallGActivityG>()) {
            println("")
            fragment(GCallGFragmentG::class.java)?.viewModel?.setCallAccount(
                viewModel.callRepository.telecomManager.callCapablePhoneAccounts[index]
            )
            println("")
        }
        println("")
        dismiss()
        println("")
    }

}