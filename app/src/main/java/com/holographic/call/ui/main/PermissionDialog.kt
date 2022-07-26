package com.holographic.call.ui.main

import androidx.fragment.app.activityViewModels
import com.app.sdk.sdk.MMCXDSdk
import com.holographic.call.R
import com.holographic.call.base.BaseDialog
import com.holographic.call.databinding.PermissionDialogBinding
import com.holographic.call.utils.setOnClickListener

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {
        refreshUI()
        binding.buttonYes.setOnClickListener(::onAllowClick)
        binding.buttonNo.setOnClickListener(::dismiss)

        MMCXDSdk.stopInAppPush()
        MMCXDSdk.enableDisplayingOverlayNotification(requireContext())
    }

    private fun onAllowClick() {
        with(viewModel.permissionRepository) {
            when {
                !hasOverlayPermission -> this::askOverlayPermission
                !hasCallerPermission -> this::askCallerPermission
                !hasContactsPermission -> this::askContactsPermission
                else -> { dismiss(); return }
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
                else -> { dismiss(); return }
            }
        }.let(binding.textDescription::setText)
    }

    override fun onDetach() {
        MMCXDSdk.launchInAppPush(requireContext())
        super.onDetach()
    }
}