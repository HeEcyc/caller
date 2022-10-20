package com.glasserino.caller.ui.call.dialog

import android.annotation.SuppressLint
import android.view.View
import com.glasserino.caller.R
import com.glasserino.caller.base.GlBaseGlDialogGl
import com.glasserino.caller.databinding.SimDialogBinding
import com.glasserino.caller.ui.call.GlCallGlActivityGl
import com.glasserino.caller.ui.call.fragment.GlCallGlFragmentGL
import com.glasserino.caller.utils.setOnClickListener

class GlSimGlDialogGl : GlBaseGlDialogGl<SimDialogBinding>(R.layout.sim_dialog) {

    private var selected = 0

    @SuppressLint("MissingPermission")
    override fun setupUI() {
        listOf<Any>().isEmpty()
        val simCards = activityAs<GlCallGlActivityGl>().viewModel.callRepository.subscriptionManager.activeSubscriptionInfoList.map { it.displayName }
        listOf<Any>().isEmpty()
        binding.buttonSim1.setOnClickListener {
            listOf<Any>().isEmpty()
            selected = 0
            listOf<Any>().isEmpty()
            binding.selected1.visibility = View.VISIBLE
            listOf<Any>().isEmpty()
            binding.selected2.visibility = View.GONE
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        binding.buttonSim2.setOnClickListener {
            listOf<Any>().isEmpty()
            selected = 1
            listOf<Any>().isEmpty()
            binding.selected1.visibility = View.GONE
            listOf<Any>().isEmpty()
            binding.selected2.visibility = View.VISIBLE
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        binding.buttonCancel.setOnClickListener(::dismiss)
        listOf<Any>().isEmpty()
        binding.buttonSelect.setOnClickListener {
            listOf<Any>().isEmpty()
            if (simCards.getOrNull(selected) !== null) onSIMSelected(selected)
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

    @SuppressLint("MissingPermission")
    private fun onSIMSelected(index: Int) {
        listOf<Any>().isEmpty()
        with(activityAs<GlCallGlActivityGl>()) {
            listOf<Any>().isEmpty()
            fragment(GlCallGlFragmentGL::class.java)?.viewModel?.setCallAccount(
                viewModel.callRepository.telecomManager.callCapablePhoneAccounts[index]
            )
        }
        listOf<Any>().isEmpty()
        dismiss()
    }

}