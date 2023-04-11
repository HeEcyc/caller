package com.paxi.cst.ui.home

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ScrollView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.children
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.paxi.cst.R
import com.paxi.cst.base.BaseFragment
import com.paxi.cst.databinding.HomeFragmentBinding
import com.paxi.cst.model.contact.UserContact
import com.paxi.cst.model.theme.NewTheme
import com.paxi.cst.model.theme.VideoTheme
import com.paxi.cst.ui.contacts.ContactsActivity
import com.paxi.cst.ui.custom.ItemDecorationWithEnds
import com.paxi.cst.ui.settings.SettingsActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.Float.min
import kotlin.math.absoluteValue

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: HomeViewModel by viewModel { parametersOf(this) }

    val contactsLauncher = registerActivityResultLauncher(ActivityResultContracts.StartActivityForResult()) {
        val contacts = it
            .data
            ?.getParcelableArrayExtra(ContactsActivity.EXTRAS_CONTACTS)?.toList() as? List<UserContact> ?: return@registerActivityResultLauncher
        viewModel.applyThemeToContacts(viewModel.adapterVP.getData()[binding.vp2.currentItem], contacts)
        AppliedDialog().show(parentFragmentManager)
    }

    private var mediaPlayer: SimpleExoPlayer? = null

    override fun setupUI() {
        binding.selector.setOnClickListener {}
        binding.root.post {
            val space = binding.root.width * 5 / 360
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
                // [0.614;1] 1 - selected; 0.614 - not selected
                val scaleValue = 0.614f + 0.386f * (1 - min(1f, position.absoluteValue))
                page.scaleX = scaleValue
                page.scaleY = scaleValue
                // 3.4028235E38 - max translation value possible
                Float.MAX_VALUE
                page.translationZ = if (position == 0f) 3.4028235E38f else min(3.4028235E38f, 1 / position.absoluteValue)
                page.translationX = -position * binding.vp2.height * 0.367f
                val icAdd = page.findViewById<View>(R.id.icAdd)
                icAdd.translationX = -position * binding.vp2.height * 0.03f
                page.findViewById<View>(R.id.overlay).visibility =
                    if (icAdd.isGone && position == 0f) View.VISIBLE else View.GONE
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
                viewModel.permissionRepository.askContactsPermission {
                    if (it) SelectDialog().apply {
                        this.theme = theme
                    }.show(parentFragmentManager)
                }
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
        binding.buttonSettings.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }
    }

    private fun stopPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        binding.imgSound.setImageResource(R.drawable.button_sound_off)
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