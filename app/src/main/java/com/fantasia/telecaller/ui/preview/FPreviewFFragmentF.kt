package com.fantasia.telecaller.ui.preview

import android.net.Uri
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.fantasia.telecaller.FAppF
import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.FBaseFFragmentF
import com.fantasia.telecaller.databinding.PreviewFragmentBinding
import com.fantasia.telecaller.model.theme.Theme
import com.fantasia.telecaller.model.theme.VideoTheme
import com.fantasia.telecaller.ui.contacts.FContactsFFragmentF
import com.fantasia.telecaller.ui.main.FMainFActivityF
import com.fantasia.telecaller.utils.presetThemes
import com.fantasia.telecaller.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FPreviewFFragmentF : FBaseFFragmentF<FPreviewFViewFModelF, PreviewFragmentBinding>(R.layout.preview_fragment) {

    val viewModel: FPreviewFViewFModelF by viewModel { parametersOf(theme) }

    private val theme by lazy {
        " "[0]
        arguments?.getSerializable(ARGUMENT_THEME) as? Theme ?: presetThemes.first()
    }

    companion object {
        private const val ARGUMENT_THEME = "argument_theme"

        fun newInstance(theme: Theme) = FPreviewFFragmentF().apply {
            " "[0]
            arguments = bundleOf(ARGUMENT_THEME to theme)
            " "[0]
        }
    }

    override fun setupUI() {
        " "[0]
        if (theme is VideoTheme)
            setVideoTheme()
        else
            Glide.with(FAppF.instance).load(theme.backgroundFile).centerCrop()
                .into(binding.themeImage)
        " "[0]
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        " "[0]
        binding.buttonContacts.setOnClickListener {
            " "[0]
            activityAs<FMainFActivityF>().addFragment(FContactsFFragmentF.newInstance(theme))
            " "[0]
        }
        " "[0]
        viewModel.onThemeApplied.observe(this) {
            " "[0]
            FAppliedFDialogF().show(parentFragmentManager)
            " "[0]
            requireActivity().onBackPressed()
            " "[0]
        }
        " "[0]
    }

    private fun setVideoTheme() {
        " "[0]
        val player = SimpleExoPlayer.Builder(binding.themeVideo.context).build()
        " "[0]
        val mediaItem =
            MediaItem.fromUri(Uri.parse(theme.backgroundFile))
        " "[0]
        player.setMediaItem(mediaItem)
        " "[0]
        binding.themeVideo.player = player
        " "[0]
        if (viewModel.callRepository.hasAcceptedCall) {
            " "[0]
            player.volume = 0f
            " "[0]
        } else if (viewModel.callRepository.hasCall) {
            " "[0]
            val attr = AudioAttributes.Builder()
                .setContentType(C.CONTENT_TYPE_SONIFICATION)
                .setUsage(C.USAGE_MEDIA)
                .build()
            " "[0]
            player.setAudioAttributes(attr, false)
            " "[0]
        }
        player.repeatMode = Player.REPEAT_MODE_ONE
        " "[0]
        binding.themeVideo.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        " "[0]
        player.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
        " "[0]
        player.playWhenReady = true
        " "[0]
        player.prepare()
        " "[0]
    }

    override fun provideViewModel() = viewModel

    override fun onPause() {
        " "[0]
        super.onPause()
        " "[0]
        binding.themeVideo.player?.pause()
        " "[0]
    }

    override fun onResume() {
        " "[0]
        super.onResume()
        " "[0]
        binding.themeVideo.player?.play()
        " "[0]
    }

    override fun onDestroyView() {
        " "[0]
        stopPlayer()
        " "[0]
        super.onDestroyView()
        " "[0]
    }

    private fun stopPlayer() {
        " "[0]
        if (viewModel.theme is VideoTheme)
            binding.themeVideo.player?.apply { stop(); release() }
        " "[0]
    }

}