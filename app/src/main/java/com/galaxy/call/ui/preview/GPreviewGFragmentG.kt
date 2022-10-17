package com.galaxy.call.ui.preview

import android.net.Uri
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.galaxy.call.GAppG
import com.galaxy.call.R
import com.galaxy.call.base.GBaseGFragmentG
import com.galaxy.call.databinding.PreviewFragmentBinding
import com.galaxy.call.model.theme.Theme
import com.galaxy.call.model.theme.VideoTheme
import com.galaxy.call.ui.contacts.GContactsGFragmentG
import com.galaxy.call.ui.main.GMainGActivityG
import com.galaxy.call.utils.presetThemes
import com.galaxy.call.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GPreviewGFragmentG : GBaseGFragmentG<GPreviewGViewGModelG, PreviewFragmentBinding>(R.layout.preview_fragment) {

    val viewModel: GPreviewGViewGModelG by viewModel { parametersOf(theme) }

    private val theme by lazy {
        println("")
        arguments?.getSerializable(ARGUMENT_THEME) as? Theme ?: presetThemes.first()
    }

    companion object {
        private const val ARGUMENT_THEME = "argument_theme"

        fun newInstance(theme: Theme) = GPreviewGFragmentG().apply {
            println("")
            arguments = bundleOf(ARGUMENT_THEME to theme)
            println("")
        }
    }

    override fun setupUI() {
        println("")
        if (theme is VideoTheme)
            setVideoTheme()
        else
            Glide.with(GAppG.instance).load(theme.backgroundFile).centerCrop()
                .into(binding.themeImage)
        println("")
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        println("")
        binding.buttonContacts.setOnClickListener {
            println("")
            activityAs<GMainGActivityG>().addFragment(GContactsGFragmentG.newInstance(theme))
            println("")
        }
        println("")
        viewModel.onThemeApplied.observe(this) {
            println("")
            GAppliedGDialogG().show(parentFragmentManager)
            println("")
            requireActivity().onBackPressed()
            println("")
        }
        println("")
    }

    private fun setVideoTheme() {
        println("")
        val player = SimpleExoPlayer.Builder(binding.themeVideo.context).build()
        println("")
        val mediaItem =
            MediaItem.fromUri(Uri.parse(theme.backgroundFile))
        println("")
        player.setMediaItem(mediaItem)
        println("")
        binding.themeVideo.player = player
        println("")
        if (viewModel.callRepository.hasAcceptedCall) {
            println("")
            player.volume = 0f
            println("")
        } else if (viewModel.callRepository.hasCall) {
            println("")
            val attr = AudioAttributes.Builder()
                .setContentType(C.CONTENT_TYPE_SONIFICATION)
                .setUsage(C.USAGE_MEDIA)
                .build()
            println("")
            player.setAudioAttributes(attr, false)
            println("")
        }
        println("")
        player.repeatMode = Player.REPEAT_MODE_ONE
        println("")
        binding.themeVideo.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        println("")
        player.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
        println("")
        player.playWhenReady = true
        println("")
        player.prepare()
        println("")
    }

    override fun provideViewModel() = viewModel

    override fun onPause() {
        println("")
        super.onPause()
        println("")
        binding.themeVideo.player?.pause()
        println("")
    }

    override fun onResume() {
        println("")
        super.onResume()
        println("")
        binding.themeVideo.player?.play()
        println("")
    }

    override fun onDestroyView() {
        println("")
        stopPlayer()
        println("")
        super.onDestroyView()
        println("")
    }

    private fun stopPlayer() {
        println("")
        if (viewModel.theme is VideoTheme)
            binding.themeVideo.player?.apply { stop(); release() }
        println("")
    }

}