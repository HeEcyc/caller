package com.megaaa.caaall.ui.main.permission

import android.content.DialogInterface
import android.text.SpannableStringBuilder
import androidx.core.text.inSpans
import androidx.fragment.app.activityViewModels
import com.megaaa.caaall.R
import com.megaaa.caaall.base.BaseDialog
import com.megaaa.caaall.databinding.PermissionDialogBinding
import com.megaaa.caaall.ui.custom.AlphaSpan
import com.megaaa.caaall.ui.main.MainViewModel
import com.megaaa.caaall.utils.setOnClickListener

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    private val mainViewModel: MainViewModel by activityViewModels()

    var onDismiss: (() -> Unit)? = null

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
            if (mainViewModel.permissionRepository.hasNecessaryPermissions)
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss?.invoke()
    }

}