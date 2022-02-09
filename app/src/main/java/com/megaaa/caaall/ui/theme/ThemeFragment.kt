package com.megaaa.caaall.ui.theme

import android.net.Uri
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.megaaa.caaall.App
import com.megaaa.caaall.R
import com.megaaa.caaall.base.BaseFragment
import com.megaaa.caaall.databinding.ThemeFragmentBinding
import com.megaaa.caaall.model.theme.Theme
import com.megaaa.caaall.model.theme.VideoTheme
import com.megaaa.caaall.utils.defaultTheme
import com.megaaa.caaall.utils.setOnClickListener
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
            Glide.with(App.instance).load(theme.backgroundFile).centerCrop().into(binding.themeImage)

        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        viewModel.closeFragment.observe(this) { requireActivity().onBackPressed() }
        binding.buttonApply.setOnClickListener { OptionsDialog().show(parentFragmentManager) }
        viewModel.onThemeApplied.observe(this) {
            SuccessDialog().show(parentFragmentManager)
            requireActivity().onBackPressed()
        }
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

    override fun onDestroyView() {
        stopPlayer()
        super.onDestroyView()
    }

    private fun stopPlayer() {
        if (viewModel.theme is VideoTheme)
            binding.themeVideo.player?.apply { stop(); release() }
    }

}