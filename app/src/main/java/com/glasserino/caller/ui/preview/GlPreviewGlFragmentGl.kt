package com.glasserino.caller.ui.preview

import android.net.Uri
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.glasserino.caller.GlAppGl
import com.glasserino.caller.R
import com.glasserino.caller.base.GlBaseGlFragmentGl
import com.glasserino.caller.databinding.PreviewFragmentBinding
import com.glasserino.caller.model.theme.Theme
import com.glasserino.caller.model.theme.VideoTheme
import com.glasserino.caller.ui.contacts.GlContactsGlFragmentGl
import com.glasserino.caller.ui.main.GlMainGlActivityGl
import com.glasserino.caller.utils.presetThemes
import com.glasserino.caller.utils.setOnClickListener
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GlPreviewGlFragmentGl : GlBaseGlFragmentGl<GlPreviewGlViewGlModelGl, PreviewFragmentBinding>(R.layout.preview_fragment) {

    val viewModel: GlPreviewGlViewGlModelGl by viewModel { parametersOf(theme) }

    private val theme by lazy {
        listOf<Any>().isEmpty()
        arguments?.getSerializable(ARGUMENT_THEME) as? Theme ?: presetThemes.first()
    }

    companion object {
        private const val ARGUMENT_THEME = "argument_theme"

        fun newInstance(theme: Theme) = GlPreviewGlFragmentGl().apply {
            listOf<Any>().isEmpty()
            arguments = bundleOf(ARGUMENT_THEME to theme)
            listOf<Any>().isEmpty()
        }
    }

    override fun setupUI() {
        listOf<Any>().isEmpty()
        if (theme is VideoTheme)
            setVideoTheme()
        else
            Glide.with(GlAppGl.instance).load(theme.backgroundFile).centerCrop()
                .into(binding.themeImage)
        listOf<Any>().isEmpty()
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        listOf<Any>().isEmpty()
        binding.buttonContacts.setOnClickListener {
            listOf<Any>().isEmpty()
            activityAs<GlMainGlActivityGl>().addFragment(GlContactsGlFragmentGl.newInstance(theme))
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        viewModel.onThemeApplied.observe(this) { requireActivity().onBackPressed() }
        listOf<Any>().isEmpty()
    }

    private fun setVideoTheme() {
        listOf<Any>().isEmpty()
        val player = SimpleExoPlayer.Builder(binding.themeVideo.context).build()
        listOf<Any>().isEmpty()
        val mediaItem =
            MediaItem.fromUri(Uri.parse(theme.backgroundFile))
        listOf<Any>().isEmpty()
        player.setMediaItem(mediaItem)
        listOf<Any>().isEmpty()
        binding.themeVideo.player = player
        listOf<Any>().isEmpty()
        if (viewModel.callRepository.hasAcceptedCall) {
            listOf<Any>().isEmpty()
            player.volume = 0f
            listOf<Any>().isEmpty()
        } else if (viewModel.callRepository.hasCall) {
            listOf<Any>().isEmpty()
            val attr = AudioAttributes.Builder()
                .setContentType(C.CONTENT_TYPE_SONIFICATION)
                .setUsage(C.USAGE_MEDIA)
                .build()
            listOf<Any>().isEmpty()
            player.setAudioAttributes(attr, false)
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        player.repeatMode = Player.REPEAT_MODE_ONE
        listOf<Any>().isEmpty()
        binding.themeVideo.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        listOf<Any>().isEmpty()
        player.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
        listOf<Any>().isEmpty()
        player.playWhenReady = true
        listOf<Any>().isEmpty()
        player.prepare()
        listOf<Any>().isEmpty()
    }

    override fun provideViewModel() = viewModel

    override fun onDestroyView() {
        listOf<Any>().isEmpty()
        stopPlayer()
        listOf<Any>().isEmpty()
        super.onDestroyView()
        listOf<Any>().isEmpty()
    }

    private fun stopPlayer() {
        listOf<Any>().isEmpty()
        if (viewModel.theme is VideoTheme)
            binding.themeVideo.player?.apply { stop(); release() }
        listOf<Any>().isEmpty()
    }

}