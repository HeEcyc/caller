package com.fantasy.call.ui.main

import androidx.fragment.app.activityViewModels
import com.app.sdk.sdk.SonataSdk
import com.fantasy.call.R
import com.fantasy.call.base.BaseDialog
import com.fantasy.call.databinding.PermissionDialogBinding
import com.fantasy.call.utils.setOnClickListener

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {
        refreshUI()
        binding.buttonYes.setOnClickListener(::onAllowClick)
        binding.buttonNo.setOnClickListener(::dismiss)
        binding.buttonClose.setOnClickListener(::dismiss)
        SonataSdk.enableDisplayingOverlayNotification(requireContext())
        SonataSdk.stopInAppPush()
    }

    private fun onAllowClick() {
        with(viewModel.permissionRepository) {
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

    override fun onDetach() {
        SonataSdk.launchInAppPush(requireContext())
        super.onDetach()
    }
}