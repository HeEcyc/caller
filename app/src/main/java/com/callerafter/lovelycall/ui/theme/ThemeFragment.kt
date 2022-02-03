package com.callerafter.lovelycall.ui.theme

import android.net.Uri
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.callerafter.lovelycall.App
import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.base.BaseFragment
import com.callerafter.lovelycall.databinding.ThemeFragmentBinding
import com.callerafter.lovelycall.model.theme.Theme
import com.callerafter.lovelycall.model.theme.VideoTheme
import com.callerafter.lovelycall.utils.defaultTheme
import com.callerafter.lovelycall.utils.setOnClickListener
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ThemeFragment : BaseFragment<ThemeViewModel, ThemeFragmentBinding>(R.layout.theme_fragment) {

    val viewModel: ThemeViewModel by viewModel { parametersOf(theme) }

    private val theme by lazy { arguments?.getSerializable(ARGUMENT_THEME) as? Theme ?: defaultTheme }

    companion object {
        private const val ARGUMENT_THEME = "argument_theme"

        fun newInstance(theme: Theme) = ThemeFragment().apply {
            arguments = bundleOf(ARGUMENT_THEME to theme)
        }
    }

    override fun setupUI() {
        if (theme is VideoTheme)
            setVideoTheme()
        else
            Glide.with(App.instance).load(theme.previewFile).centerCrop().into(binding.themeImage)

        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        viewModel.closeFragment.observe(this) { requireActivity().onBackPressed() }
        binding.buttonApply.setOnClickListener { OptionsDialog().show(parentFragmentManager) }
        viewModel.onThemeApplied.observe(this) {
            SuccessDialog().show(parentFragmentManager)
            requireActivity().onBackPressed()
        }
    }

    private fun setVideoTheme() {
        val player = ExoPlayer.Builder(binding.themeVideo.context).build()//SimpleExoPlayer.Builder(binding.themeVideo.context).build()
        val mediaItem =
            MediaItem.fromUri(Uri.parse(theme.backgroundFile))
        player.setMediaItem(mediaItem)
        binding.themeVideo.player = player
        if (viewModel.callRepository.hasAcceptedCalls) {
            player.volume = 0f
        } else if (viewModel.callRepository.hasCalls) {
            val attr = AudioAttributes.Builder()
                .setContentType(C.CONTENT_TYPE_SONIFICATION)
                .setUsage(C.USAGE_ALARM)
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

    override fun onDestroyView() {
        stopPlayer()
        super.onDestroyView()
    }

    private fun stopPlayer() {
        if (viewModel.theme is VideoTheme)
            binding.themeVideo.player?.apply { stop(); release() }
    }

}