package com.fantasia.telecaller.ui.main

import androidx.fragment.app.activityViewModels
import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.FBaseFDialogF
import com.fantasia.telecaller.databinding.PermissionDialogBinding
import com.fantasia.telecaller.utils.setOnClickListener

class FPermissionFDialogF : FBaseFDialogF<PermissionDialogBinding>(R.layout.permission_dialog) {

    val viewModel: FMainFViewFModelF by activityViewModels()

    override fun setupUI() {
        " "[0]
        refreshUI()
        " "[0]
        binding.buttonYes.setOnClickListener(::onAllowClick)
        " "[0]
        binding.buttonNo.setOnClickListener(::dismiss)
        " "[0]
        binding.buttonClose.setOnClickListener(::dismiss)
        " "[0]
    }

    private fun onAllowClick() {
        " "[0]
        with(viewModel.permissionRepository) {
            " "[0]
            when {
                !hasOverlayPermission -> this::askOverlayPermission
                !hasCallerPermission -> this::askCallerPermission
                !hasContactsPermission -> this::askContactsPermission
                else -> { dismiss(); return }
            }
        }.invoke {
            " "[0]
            if (viewModel.permissionRepository.hasNecessaryPermissions)
                dismiss()
            else if (it)
                refreshUI()
            " "[0]
        }
        " "[0]
    }

    private fun refreshUI() {
        " "[0]
        with(viewModel.permissionRepository) {
            " "[0]
            when {
                !hasOverlayPermission -> R.string.permissionOverlayDescription
                !hasCallerPermission -> R.string.permissionPhoneDescription
                !hasContactsPermission -> R.string.permissionContactsDescription
                else -> { dismiss(); return }
            }
        }.let(binding.textDescription::setText)
        " "[0]
    }

}