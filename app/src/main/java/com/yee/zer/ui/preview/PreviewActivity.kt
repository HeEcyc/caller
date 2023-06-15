package com.yee.zer.ui.preview

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.yee.zer.App
import com.yee.zer.R
import com.yee.zer.base.BaseActivity
import com.yee.zer.databinding.PreviewActivityBinding
import com.yee.zer.model.theme.Theme
import com.yee.zer.model.theme.VideoTheme
import com.yee.zer.ui.home.SelectDialog
import com.yee.zer.utils.presetThemes
import com.yee.zer.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PreviewActivity : BaseActivity<PreviewViewModel, PreviewActivityBinding>() {

    val viewModel: PreviewViewModel by viewModel { parametersOf(theme) }

    private val theme by lazy {
        intent?.getSerializableExtra(ARGUMENT_THEME) as? Theme ?: presetThemes.first()
    }

    override fun provideLayoutId() = R.layout.preview_activity

    companion object {
        private const val ARGUMENT_THEME = "argument_theme"

        fun newIntent(theme: Theme, context: Context) = Intent(context, PreviewActivity::class.java).apply {
            putExtras(bundleOf(ARGUMENT_THEME to theme))
        }
    }

    override fun setupUI() {
        if (theme is VideoTheme)
            setVideoTheme()
        else
            Glide.with(App.instance).load(theme.backgroundFile).centerCrop()
                .into(binding.themeImage)

        binding.buttonBack.setOnClickListener(::onBackPressed)
        binding.buttonApply.setOnClickListener {
            SelectDialog().apply {
                this.theme = this@PreviewActivity.theme
            }.show(supportFragmentManager)
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

    override fun onPause() {
        super.onPause()
        binding.themeVideo.player?.pause()
    }

    override fun onResume() {
        super.onResume()
        binding.themeVideo.player?.play()
    }

    override fun onDestroy() {
        stopPlayer()
        super.onDestroy()
    }

    private fun stopPlayer() {
        if (viewModel.theme is VideoTheme)
            binding.themeVideo.player?.apply { stop(); release() }
    }

}