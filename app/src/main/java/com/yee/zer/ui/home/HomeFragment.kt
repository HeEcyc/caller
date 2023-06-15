package com.yee.zer.ui.home

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.yee.zer.R
import com.yee.zer.base.BaseFragment
import com.yee.zer.databinding.HomeFragmentBinding
import com.yee.zer.model.contact.UserContact
import com.yee.zer.model.theme.VideoTheme
import com.yee.zer.ui.contacts.ContactsActivity
import com.yee.zer.ui.custom.ItemDecorationWithEnds
import com.yee.zer.ui.themes.ThemesActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: HomeViewModel by viewModel { parametersOf(this) }

    val contactsLauncher = registerActivityResultLauncher(ActivityResultContracts.StartActivityForResult()) {
        val contacts = it
            .data
            ?.getParcelableArrayExtra(ContactsActivity.EXTRAS_CONTACTS)?.toList() as? List<UserContact> ?: return@registerActivityResultLauncher
        viewModel.applyThemeToContacts(viewModel.theme!!, contacts)
        AppliedDialog().show(parentFragmentManager)
    }

    private var mediaPlayer: SimpleExoPlayer? = null

    override fun setupUI() {
        binding.root.post {
            val space = binding.root.width * 8 / 360
            val itemDecoration = ItemDecorationWithEnds(
                left = space,
                right = space
            )
            binding.recyclerView.addItemDecoration(itemDecoration)
        }
        binding.buttonApply.setOnClickListener {
            viewModel.permissionRepository.askContactsPermission {
                if (it) SelectDialog().apply {
                    this.theme = viewModel.theme
                }.show(parentFragmentManager)
            }
        }
        binding.buttonAdd.setOnClickListener {
            viewModel.permissionRepository.askStoragePermissions(lifecycleScope) {
                if (it) viewModel.addNewTheme()
            }
        }
        binding.buttonAll.setOnClickListener {
            startActivity(Intent(requireContext(), ThemesActivity::class.java))
        }
        viewModel.onThemeSelected.observe(this) {
            Glide.with(this).load(it.previewFile).into(binding.previewSmall)
            Glide.with(this).load(it.previewFile).into(binding.previewBig)
        }
        binding.buttonSound.setOnClickListener {
            val theme = viewModel.theme
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
        binding.switchPower.isChecked = viewModel.permissionRepository.hasCallerPermission
        binding.buttonPower.setOnClickListener {
            with(viewModel.permissionRepository) {
                if (hasCallerPermission)
                    openDefaultPhoneSelection(requireContext())
                else
                    askCallerPermission {}
            }
        }
        binding.menu.setOnClickListener {}
        binding.buttonSettings.setOnClickListener { binding.menu.visibility = View.VISIBLE }
        binding.buttonBackSettings.setOnClickListener { binding.menu.visibility = View.GONE }
        binding.buttonLanguage.setOnClickListener {
            binding.menuSettings.visibility = View.GONE
            binding.headerButtonSettings.visibility = View.GONE
            binding.headerTextSettings.visibility = View.GONE
            binding.menuLanguage.visibility = View.VISIBLE
            binding.headerButtonLanguage.visibility = View.VISIBLE
            binding.headerTextLanguage.visibility = View.VISIBLE
        }
        binding.buttonBackLanguage.setOnClickListener {
            binding.menuLanguage.visibility = View.GONE
            binding.headerButtonLanguage.visibility = View.GONE
            binding.headerTextLanguage.visibility = View.GONE
            binding.menuSettings.visibility = View.VISIBLE
            binding.headerButtonSettings.visibility = View.VISIBLE
            binding.headerTextSettings.visibility = View.VISIBLE
        }
        binding.buttonOpenAdd.setOnClickListener {
            binding.layoutPreview.visibility = View.GONE
            binding.layoutAdd.visibility = View.VISIBLE
        }
        binding.buttonOpenPreview.setOnClickListener {
            binding.layoutAdd.visibility = View.GONE
            binding.layoutPreview.visibility = View.VISIBLE
        }
    }

    private fun stopPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        binding.buttonSound.setImageResource(R.drawable.button_sound_off)
    }

    override fun onResume() {
        super.onResume()
        binding.switchPower.isChecked = viewModel.permissionRepository.hasCallerPermission
    }

    override fun onPause() {
        super.onPause()
        stopPlayer()
    }

    override fun provideViewModel() = viewModel

}