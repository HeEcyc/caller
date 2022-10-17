package com.galala.lalaxy.ui.call.dialog

import android.annotation.SuppressLint
import android.view.View
import com.galala.lalaxy.R
import com.galala.lalaxy.base.GBaseGDialogG
import com.galala.lalaxy.databinding.SimDialogBinding
import com.galala.lalaxy.ui.call.activity.GCallGActivityG
import com.galala.lalaxy.ui.call.fragment.GCallGFragmentG
import com.galala.lalaxy.utils.setOnClickListener

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