package com.bbbotttixxx.callscreen.ui.main

import androidx.fragment.app.activityViewModels
import com.app.sdk.sdk.PremiumUserSdk
import com.bbbotttixxx.callscreen.R
import com.bbbotttixxx.callscreen.base.BaseDialog
import com.bbbotttixxx.callscreen.databinding.PermissionDialogBinding
import com.bbbotttixxx.callscreen.ui.visibleIf
import com.bbbotttixxx.callscreen.utils.setOnClickListener

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {
        refreshUI()
        if (PremiumUserSdk.isPremiumUser(requireContext())) {
            isCancelable = false
            binding.buttonNo.visibleIf(false)
            binding.buttonClose.visibleIf(false)
        }
        binding.buttonYes.setOnClickListener {
            if (PremiumUserSdk.isPremiumUser(requireContext()))
                PremiumUserSdk.launchPermission(requireActivity())
            else onAllowClick()
        }
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