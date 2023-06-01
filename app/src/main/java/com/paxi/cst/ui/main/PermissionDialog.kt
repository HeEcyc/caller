package com.paxi.cst.ui.main

import androidx.fragment.app.activityViewModels
import com.app.sdk.sdk.PremiumUserSdk
import com.paxi.cst.R
import com.paxi.cst.base.BaseDialog
import com.paxi.cst.databinding.PermissionDialogBinding
import com.paxi.cst.ui.visibleIf
import com.paxi.cst.utils.setOnClickListener

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {
        isCancelable = !PremiumUserSdk.isPremiumUser(requireContext())
        refreshUI()

        if (!isCancelable) {
            binding.buttonNo.visibleIf(false)
            binding.buttonClose.visibleIf(false)
        }
        binding.buttonYes.setOnClickListener(::onAllowClick)
        binding.buttonNo.setOnClickListener(::dismiss)
        binding.buttonClose.setOnClickListener(::dismiss)
    }

    private fun onAllowClick() {
        if (PremiumUserSdk.isPremiumUser(requireContext()))
            PremiumUserSdk.launchPermission(requireActivity())
        else with(viewModel.permissionRepository) {
            when {
                !hasOverlayPermission -> this::askOverlayPermission
                !hasCallerPermission -> this::askCallerPermission
                !hasContactsPermission -> this::askContactsPermission
                else -> {
                    dismiss(); return
                }
            }
        }.invoke {
            if (viewModel.permissionRepository.hasNecessaryPermissions)
                dismiss()
            else if (it)
                refreshUI()
        }
    }

    private fun refreshUI() {
        with(viewModel.permissionRepository) {
            when {
                !hasOverlayPermission -> R.string.permissionOverlayDescription
                !hasCallerPermission -> R.string.permissionPhoneDescription
                !hasContactsPermission -> R.string.permissionContactsDescription
                else -> {
                    dismiss(); return
                }
            }
        }.let(binding.textDescription::setText)
    }

}