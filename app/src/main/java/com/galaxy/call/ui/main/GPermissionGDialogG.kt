package com.galaxy.call.ui.main

import androidx.fragment.app.activityViewModels
import com.galaxy.call.R
import com.galaxy.call.base.GBaseGDialogG
import com.galaxy.call.databinding.PermissionDialogBinding
import com.galaxy.call.utils.setOnClickListener

class GPermissionGDialogG : GBaseGDialogG<PermissionDialogBinding>(R.layout.permission_dialog) {

    val viewModel: GMainGViewGModelG by activityViewModels()

    override fun setupUI() {
        println("")
        refreshUI()
        println("")
        binding.buttonYes.setOnClickListener(::onAllowClick)
        println("")
        binding.buttonNo.setOnClickListener(::dismiss)
        println("")
        binding.buttonClose.setOnClickListener(::dismiss)
        println("")
    }

    private fun onAllowClick() {
        println("")
        with(viewModel.permissionRepository) {
            println("")
            when {
                !hasOverlayPermission -> this::askOverlayPermission
                !hasCallerPermission -> this::askCallerPermission
                !hasContactsPermission -> this::askContactsPermission
                else -> { dismiss(); return }
            }
        }.invoke {
            println("")
            if (viewModel.permissionRepository.hasNecessaryPermissions)
                dismiss()
            else if (it)
                refreshUI()
            println("")
        }
        println("")
    }

    private fun refreshUI() {
        println("")
        with(viewModel.permissionRepository) {
            println("")
            when {
                !hasOverlayPermission -> R.string.permissionOverlayDescription
                !hasCallerPermission -> R.string.permissionPhoneDescription
                !hasContactsPermission -> R.string.permissionContactsDescription
                else -> { dismiss(); return }
            }
        }.let(binding.textDescription::setText)
        println("")
    }

}