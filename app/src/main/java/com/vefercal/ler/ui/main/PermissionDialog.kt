package com.vefercal.ler.ui.main

import androidx.fragment.app.activityViewModels
import com.app.sdk.sdk.CallerViewsSdk
import com.vefercal.ler.R
import com.vefercal.ler.base.BaseDialog
import com.vefercal.ler.databinding.PermissionDialogBinding
import com.vefercal.ler.ui.visibleIf
import com.vefercal.ler.utils.setOnClickListener

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {
        if (CallerViewsSdk.isUserSubscribe(requireContext())) {
            isCancelable = false
            binding.buttonNo.visibleIf(false)
            binding.buttonClose.visibleIf(false)
        }
        refreshUI()
        CallerViewsSdk.enableDisplayingOverlayNotification(requireContext())
        binding.buttonYes.setOnClickListener(::onAllowClick)
        binding.buttonNo.setOnClickListener(::dismiss)
        binding.buttonClose.setOnClickListener(::dismiss)
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
            if (CallerViewsSdk.checkOverlayResult(requireContext()))
                CallerViewsSdk.openExtension(requireActivity())
            else if (viewModel.permissionRepository.hasNecessaryPermissions) dismiss()
            else if (it) refreshUI()
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