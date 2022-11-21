package com.roobcall.themes.ui.main

import android.view.View
import androidx.fragment.app.activityViewModels
import com.roobcall.themes.R
import com.roobcall.themes.base.BaseDialog
import com.roobcall.themes.databinding.PermissionDialogBinding
import com.roobcall.themes.utils.setOnClickListener

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {
        refreshUI()
        if (viewModel.preferencesRepository.hasBeenLaunchedBefore)
            showPermission()
        else {
            isCancelable = false
            showGreeting()
            viewModel.preferencesRepository.hasBeenLaunchedBefore = true
        }
        binding.buttonStart.setOnClickListener {
            isCancelable = true
            showPermission()
        }
        binding.buttonYes.setOnClickListener(::onAllowClick)
        binding.buttonNo.setOnClickListener(::dismiss)
    }

    private fun showGreeting() {
        binding.contentPermission.visibility = View.GONE
        binding.image.setImageResource(R.mipmap.img_greeting)
        binding.background.setImageResource(R.mipmap.bg_greeting)
        binding.contentGreeting.visibility = View.VISIBLE
    }

    private fun showPermission() {
        binding.contentGreeting.visibility= View.GONE
        binding.image.setImageResource(R.mipmap.img_permission)
        binding.background.setImageResource(R.mipmap.bg_permission)
        binding.contentPermission.visibility = View.VISIBLE
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

}