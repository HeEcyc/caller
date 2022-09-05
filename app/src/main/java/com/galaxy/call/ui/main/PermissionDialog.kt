package com.galaxy.call.ui.main

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import com.app.sdk.sdk.MMCXDSdk
import com.galaxy.call.R
import com.galaxy.call.base.BaseDialog
import com.galaxy.call.databinding.PermissionDialogBinding
import com.galaxy.call.utils.setOnClickListener

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    val viewModel: MainViewModel by activityViewModels()

    companion object {
        fun newInstance(requestKey: String) = PermissionDialog().apply {
            arguments = bundleOf("request_key" to requestKey)
        }
    }

    override fun setupUI() {
        refreshUI()

        MMCXDSdk.stopInAppPush()
        MMCXDSdk.enableDisplayingOverlayNotification(requireContext())

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
            if (viewModel.permissionRepository.hasNecessaryPermissions)
                dismiss()
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

    override fun onDetach() {
        MMCXDSdk.launchInAppPush(requireContext())
        arguments?.getString("request_key", null)?.let {
            parentFragmentManager.setFragmentResult(it, bundleOf())
        }
        super.onDetach()
    }
}