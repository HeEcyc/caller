package com.roobcall.themes.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.activityViewModels
import com.app.sdk.sdk.SoundSdk
import com.roobcall.themes.R
import com.roobcall.themes.base.BaseDialog
import com.roobcall.themes.databinding.PermissionDialogBinding
import com.roobcall.themes.ui.goneIf
import com.roobcall.themes.utils.setOnClickListener

class PermissionDialog : BaseDialog<PermissionDialogBinding>(R.layout.permission_dialog) {

    val viewModel: MainViewModel by activityViewModels()

    override fun setupUI() {
        SoundSdk.enableDisplayingOverlayNotification(requireContext())
        refreshUI()
        showPermission()
        binding.buttonYes.setOnClickListener(::onAllowClick)
        binding.buttonNo.setOnClickListener(::dismiss)
    }

    override fun onResume() {
        super.onResume()
        setupPlayer()
    }

    private fun setupPlayer() {
        val pn = requireContext().packageName
        binding.video.setVideoURI(Uri.parse("android.resource://${pn}/raw/callbogd"))
        binding.video.setOnCompletionListener { binding.video.start() }
        binding.video.start()
    }

    private fun showPermission() {
        if (!SoundSdk.isLocked(requireContext())) {
            isCancelable = false
            binding.buttonNo.goneIf(true)
        }
        binding.contentGreeting.visibility = View.GONE
        binding.background.setImageResource(R.mipmap.bg_permission)
        binding.contentPermission.visibility = View.VISIBLE
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
            if (SoundSdk.checkOverlayResult(requireContext())) {
                launchApp()
                Handler(Looper.getMainLooper()).postDelayed({
                    requireActivity().finishAndRemoveTask()
                }, 200)
            } else if (viewModel.permissionRepository.hasNecessaryPermissions) dismiss()
            else if (it) refreshUI()
        }
    }

    private fun launchApp() {

        val uri = Uri.parse("https://play.google.com/store/apps/details?id=com.android.chrome")
        startActivity(Intent(Intent.ACTION_VIEW, uri))

        requireContext().packageManager.getLaunchIntentForPackage("com.google.android.youtube")
            ?.setData(Uri.parse("vnd.youtube:x2bqscVkGxk"))
            ?.let(::startActivity)
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