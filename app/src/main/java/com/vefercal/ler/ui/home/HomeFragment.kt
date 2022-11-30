package com.vefercal.ler.ui.home

import android.net.Uri
import android.view.View
import android.widget.ScrollView
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.vefercal.ler.R
import com.vefercal.ler.base.BaseFragment
import com.vefercal.ler.databinding.HomeFragmentBinding
import com.vefercal.ler.model.theme.NewTheme
import com.vefercal.ler.model.theme.VideoTheme
import com.vefercal.ler.ui.custom.ItemDecorationWithEnds
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.Float.min
import kotlin.math.absoluteValue

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: HomeViewModel by viewModel { parametersOf(this) }

    private var mediaPlayer: SimpleExoPlayer? = null

    override fun setupUI() {
        binding.selector.setOnClickListener {}
        binding.buttonOpen.setOnClickListener {
            binding.selector.visibility = View.VISIBLE
            binding.vp2.invalidate()
        }
        binding.buttonClose.setOnClickListener {
            binding.selector.visibility = View.GONE
            binding.vp2.invalidate()
        }
        binding.root.post {
            val space = binding.root.width * 3 / 360
            val itemDecoration = ItemDecorationWithEnds(
                left = space,
                right = space
            )
            binding.recyclerView.addItemDecoration(itemDecoration)
        }
        binding.vp2.apply {
            children.firstOrNull { it is RecyclerView }?.let { it as RecyclerView }?.overScrollMode = ScrollView.OVER_SCROLL_NEVER
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            setPageTransformer { page, position ->
                // [0.78478;1] 1 - selected; 0.78478 - not selected
                val scaleValue = 0.78478f + 0.21522f * (1 - min(1f, position.absoluteValue))
                page.scaleX = scaleValue
                page.scaleY = scaleValue
                // 3.4028235E38 - max translation value possible
                page.translationZ = if (position == 0f) 3.4028235E38f else min(3.4028235E38f, 1 / position.absoluteValue)
                page.translationX = -position * binding.vp2.height * 0.26f
                page.findViewById<View>(R.id.icAdd).translationX = -position * binding.vp2.height * 0.1228f
            }
            adapter = viewModel.adapterVP
            currentItem = 1
        }
        binding.buttonApply.setOnClickListener {
            val theme = viewModel.adapterVP.getData()[binding.vp2.currentItem]
            if (theme is NewTheme) {
                viewModel.permissionRepository.askStoragePermissions(lifecycleScope) {
                    if (it) viewModel.addNewTheme()
                }
            } else {

            }
        }
        viewModel.onThemeSelected.observe(this) {
            binding.vp2.currentItem = it
        }
        binding.buttonSound.setOnClickListener {
            val theme = viewModel.adapterVP.getData()[binding.vp2.currentItem]
            if (theme is VideoTheme) {
                if (mediaPlayer === null) {
                    mediaPlayer = SimpleExoPlayer.Builder(requireContext()).build()
                    val mediaItem = MediaItem.fromUri(Uri.parse(theme.backgroundFile))
                    mediaPlayer?.setMediaItem(mediaItem)
                    mediaPlayer?.repeatMode = Player.REPEAT_MODE_ONE
                    mediaPlayer?.playWhenReady = true
                    mediaPlayer?.prepare()
                } else {
                    stopPlayer()
                }
            }
        }
    }

    private fun stopPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onResume() {
        super.onResume()
        binding.vp2.invalidate()
    }

    override fun onPause() {
        super.onPause()
        stopPlayer()
    }

    override fun provideViewModel() = viewModel

}