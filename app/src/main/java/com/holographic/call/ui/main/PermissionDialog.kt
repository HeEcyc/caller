package com.holographic.call.ui.main

import androidx.fragment.app.activityViewModels
import com.holographic.call.R
import com.holographic.call.base.BaseDialog
import com.holographic.call.databinding.PermissionDialogBinding

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {
        refreshUI()
        binding.buttonYes.setOnClickListener(::onAllowClick)
        binding.buttonNo.setOnClickListener(::dismiss)
    }

    private fun onAllowClick() {
        with(viewModel.permissionRepository) {
            when {
                !hasCallerPermission -> this::askCallerPermission
                !hasContactsPermission -> this::askContactsPermission
                !hasOverlayPermission -> this::askOverlayPermission
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
                !hasCallerPermission -> R.string.permissionPhoneDescription
                !hasContactsPermission -> R.string.permissionContactsDescription
                !hasOverlayPermission -> R.string.permissionOverlayDescription
                else -> { dismiss(); return }
            }
        }.let(binding.textDescription::setText)
    }

}