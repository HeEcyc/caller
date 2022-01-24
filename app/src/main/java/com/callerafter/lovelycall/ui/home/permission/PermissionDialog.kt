package com.callerafter.lovelycall.ui.home.permission

import android.text.SpannableStringBuilder
import androidx.core.text.inSpans
import androidx.fragment.app.activityViewModels
import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.base.BaseDialog
import com.callerafter.lovelycall.databinding.PermissionDialogBinding
import com.callerafter.lovelycall.ui.custom.AlphaSpan
import com.callerafter.lovelycall.ui.main.MainViewModel
import com.callerafter.lovelycall.utils.setOnClickListener

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun setupUI() {
        isCancelable = false
        refreshUI()
        binding.buttonAllow.setOnClickListener(::onAllowClick)
    }

    private fun onAllowClick() {
        with(mainViewModel.permissionRepository) {
            when {
                !hasCallerPermission -> this::askCallerPermission
                !hasContactsPermission -> this::askContactsPermission
                !hasOverlayPermission -> this::askOverlayPermission
                else -> throw IllegalStateException("No more permissions to handle")
            }
        }.invoke {
            if (it && mainViewModel.permissionRepository.hasOverlayPermission)
                dismiss()
            else if (it)
                refreshUI()
        }
    }

    private fun refreshUI() {
        with(mainViewModel.permissionRepository) {
            when {
                !hasCallerPermission -> R.string.permissionPhoneDescription
                !hasContactsPermission -> R.string.permissionContactsDescription
                !hasOverlayPermission -> R.string.permissionOverlayDescription
                else -> throw IllegalStateException("No more permissions to handle")
            }
        }.let(binding.description::setText)
        val currentPermission = 1 + with(mainViewModel.permissionRepository) {
            listOf(hasCallerPermission, hasContactsPermission, hasOverlayPermission)
        }.count { it }
        binding.counter.text = SpannableStringBuilder()
            .append("$currentPermission")
            .inSpans(AlphaSpan()) { append(" / 3") }
    }

}