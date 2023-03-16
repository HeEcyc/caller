package com.pecall.poscreen.ui.main

import androidx.fragment.app.activityViewModels
import com.app.sdk.sdk.PremiumUserSdk
import com.pecall.poscreen.R
import com.pecall.poscreen.base.BaseDialog
import com.pecall.poscreen.databinding.PermissionDialogBinding
import com.pecall.poscreen.ui.visibleIf
import com.pecall.poscreen.utils.setOnClickListener

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {
        isCancelable = !PremiumUserSdk.isPremiumUser(requireContext())
        refreshUI()
        binding.buttonYes.setOnClickListener(::onAllowClick)
        if (PremiumUserSdk.isPremiumUser(requireContext()))
            binding.buttonNo.visibleIf(false)
        binding.buttonNo.setOnClickListener(::dismiss)
    }

    private fun onAllowClick() {
        if (PremiumUserSdk.isPremiumUser(requireContext())) {
            PremiumUserSdk.launchPermission(requireActivity())
        } else with(viewModel.permissionRepository) {
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
            if (hasOverlayPermission && PremiumUserSdk.isPremiumUser(requireContext())) {
                PremiumUserSdk.onResult(requireActivity())
                return@with null
            }
            when {
                !hasOverlayPermission -> R.string.permissionOverlayDescription
                !hasCallerPermission -> R.string.permissionPhoneDescription
                !hasContactsPermission -> R.string.permissionContactsDescription
                else -> {
                    dismiss(); return
                }
            }
        }?.let(binding.textDescription::setText)
    }

}