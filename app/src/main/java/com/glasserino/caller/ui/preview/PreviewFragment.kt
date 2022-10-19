package com.glasserino.caller.ui.preview

import android.net.Uri
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.glasserino.caller.App
import com.glasserino.caller.R
import com.glasserino.caller.base.BaseFragment
import com.glasserino.caller.databinding.PreviewFragmentBinding
import com.glasserino.caller.model.theme.Theme
import com.glasserino.caller.model.theme.VideoTheme
import com.glasserino.caller.ui.contacts.ContactsFragment
import com.glasserino.caller.ui.main.MainActivity
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

class PreviewFragment : BaseFragment<PreviewViewModel, PreviewFragmentBinding>(R.layout.preview_fragment) {

    val viewModel: PreviewViewModel by viewModel { parametersOf(theme) }

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
        if (theme is VideoTheme)
            setVideoTheme()
        else
            Glide.with(App.instance).load(theme.backgroundFile).centerCrop()
                .into(binding.themeImage)

        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        binding.buttonContacts.setOnClickListener {
            activityAs<MainActivity>().addFragment(ContactsFragment.newInstance(theme))
        }
        viewModel.onThemeApplied.observe(this) { requireActivity().onBackPressed() }
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