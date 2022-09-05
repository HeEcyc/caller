package com.galaxy.call.ui.preview

import android.net.Uri
import android.provider.Settings
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.galaxy.call.App
import com.galaxy.call.R
import com.galaxy.call.base.BaseFragment
import com.galaxy.call.databinding.PreviewFragmentBinding
import com.galaxy.call.model.theme.Theme
import com.galaxy.call.model.theme.VideoTheme
import com.galaxy.call.ui.contacts.ContactsFragment
import com.galaxy.call.ui.main.MainActivity
import com.galaxy.call.ui.main.PermissionDialog
import com.galaxy.call.utils.presetThemes
import com.galaxy.call.utils.setOnClickListener
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PreviewFragment :
    BaseFragment<PreviewViewModel, PreviewFragmentBinding>(R.layout.preview_fragment) {

    val viewModel: PreviewViewModel by viewModel { parametersOf(theme) }
    var currentAction: (() -> Unit)? = null

    private val theme by lazy {
        arguments?.getSerializable(ARGUMENT_THEME) as? Theme ?: presetThemes.first()
    }

    companion object {
        private const val ARGUMENT_THEME = "argument_theme"

        fun newInstance(theme: Theme) = PreviewFragment().apply {
            arguments = bundleOf(ARGUMENT_THEME to theme)
        }
    }

    override fun setupUI() {
        setFragmentResultListener("preview") { key, _ ->
            if (key != "preview") return@setFragmentResultListener
            if (Settings.canDrawOverlays(requireContext())) currentAction?.invoke()
            else ErrorDialog.newInstance("preview")
                .show(parentFragmentManager, "sd")
        }


        if (theme is VideoTheme)
            setVideoTheme()
        else
            Glide.with(App.instance).load(theme.backgroundFile).centerCrop()
                .into(binding.themeImage)

        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        binding.buttonContacts.setOnClickListener {
            activityAs<MainActivity>().addFragment(ContactsFragment.newInstance(theme))
        }
        viewModel.onThemeApplied.observe(this) {
            AppliedDialog().show(parentFragmentManager)
            requireActivity().onBackPressed()
        }
        binding.applyButton.setOnClickListener {
            currentAction = { viewModel.applyToAll() }
            if (Settings.canDrawOverlays(requireContext())) currentAction?.invoke()
            else showPermissionDialog()
        }
    }

    private fun showPermissionDialog() {
        PermissionDialog.newInstance("preview")
            .show(parentFragmentManager, "preview")
    }

    private fun setVideoTheme() {
        val player = SimpleExoPlayer.Builder(binding.themeVideo.context).build()
        val mediaItem =
            MediaItem.fromUri(Uri.parse(theme.backgroundFile))
        player.setMediaItem(mediaItem)
        binding.themeVideo.player = player
        if (viewModel.callRepository.hasAcceptedCall) {
            player.volume = 0f
        } else if (viewModel.callRepository.hasCall) {
            val attr = AudioAttributes.Builder()
                .setContentType(C.CONTENT_TYPE_SONIFICATION)
                .setUsage(C.USAGE_MEDIA)
                .build()
            player.setAudioAttributes(attr, false)
        }
        player.repeatMode = Player.REPEAT_MODE_ONE
        binding.themeVideo.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        player.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
        player.playWhenReady = true
        player.prepare()
    }

    override fun provideViewModel() = viewModel

    override fun onPause() {
        super.onPause()
        binding.themeVideo.player?.pause()
    }

    override fun onResume() {
        super.onResume()
        binding.themeVideo.player?.play()
    }

    override fun onDestroyView() {
        stopPlayer()
        super.onDestroyView()
    }

    private fun stopPlayer() {
        if (viewModel.theme is VideoTheme)
            binding.themeVideo.player?.apply { stop(); release() }
    }

}