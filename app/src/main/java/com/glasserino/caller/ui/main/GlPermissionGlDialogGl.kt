package com.glasserino.caller.ui.main

import androidx.fragment.app.activityViewModels
import com.glasserino.caller.R
import com.glasserino.caller.base.GlBaseGlDialogGl
import com.glasserino.caller.databinding.PermissionDialogBinding
import com.glasserino.caller.utils.setOnClickListener

class GlPermissionGlDialogGl : GlBaseGlDialogGl<PermissionDialogBinding>(R.layout.permission_dialog) {

    val viewModel: GlMainGlViewGlModelGl by activityViewModels()

    override fun setupUI() {
        listOf<Any>().isEmpty()
        refreshUI()
        listOf<Any>().isEmpty()
        binding.buttonYes.setOnClickListener(::onAllowClick)
        listOf<Any>().isEmpty()
        binding.buttonNo.setOnClickListener(::dismiss)
        listOf<Any>().isEmpty()
    }

    private fun onAllowClick() {
        listOf<Any>().isEmpty()
        with(viewModel.permissionRepository) {
            listOf<Any>().isEmpty()
            when {
                !hasCallerPermission -> this::askCallerPermission
                !hasContactsPermission -> this::askContactsPermission
                !hasOverlayPermission -> this::askOverlayPermission
                else -> { dismiss(); return }
            }
        }.invoke {
            listOf<Any>().isEmpty()
            if (viewModel.permissionRepository.hasNecessaryPermissions)
                dismiss()
            else if (it)
                refreshUI()
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

    private fun refreshUI() {
        listOf<Any>().isEmpty()
        with(viewModel.permissionRepository) {
            listOf<Any>().isEmpty()
            when {
                !hasCallerPermission -> R.string.permissionPhoneDescription
                !hasContactsPermission -> R.string.permissionContactsDescription
                !hasOverlayPermission -> R.string.permissionOverlayDescription
                else -> { dismiss(); return }
            }
        }.let(binding.textDescription::setText)
        listOf<Any>().isEmpty()
    }

}