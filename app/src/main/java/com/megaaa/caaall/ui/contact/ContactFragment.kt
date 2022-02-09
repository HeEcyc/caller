package com.megaaa.caaall.ui.contact

import android.net.Uri
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.megaaa.caaall.App
import com.megaaa.caaall.R
import com.megaaa.caaall.base.BaseActivity
import com.megaaa.caaall.base.BaseFragment
import com.megaaa.caaall.databinding.ContactFragmentBinding
import com.megaaa.caaall.model.contact.UserContact
import com.megaaa.caaall.model.theme.VideoTheme
import com.megaaa.caaall.repository.PermissionRepository
import com.megaaa.caaall.ui.home.HomeFragment
import com.megaaa.caaall.ui.main.MainActivity
import com.megaaa.caaall.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ContactFragment : BaseFragment<ContactViewModel, ContactFragmentBinding>(R.layout.contact_fragment) {

    val viewModel: ContactViewModel by viewModel { parametersOf(contact) }

    private val contact by lazy {
        arguments?.getSerializable(ARGUMENT_CONTACT) as? UserContact ?: UserContact()
    }

    companion object {
        private const val ARGUMENT_CONTACT = "argument_contact"

        fun newInstance(contact: UserContact) = ContactFragment().apply {
            arguments = bundleOf(ARGUMENT_CONTACT to contact)
        }
    }

    override fun setupUI() {
        viewModel.permissionRepository = PermissionRepository(this)
        if (viewModel.theme is VideoTheme)
            setVideoTheme()
        else
            Glide.with(App.instance).load(viewModel.theme.backgroundFile).centerCrop().into(binding.themeImage)
        Glide.with(App.instance).let {
            if (contact.photoThumbnailUri !== null) it.load(contact.photoThumbnailUri)
            else it.load(R.mipmap.ic_no_logo)
        }.into(binding.contactThumbnail)

        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        viewModel.callNumber.observe(this) {
            viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) { res ->
                if (res) activityAs<BaseActivity<*, *>>().call(it)
            }
        }
        binding.buttonEdit.setOnClickListener {
            stopPlayer()
            activityAs<MainActivity>().addFragment(HomeFragment.newInstance(HomeFragment.Mode.THEME_PICKER))
        }
        viewModel.recreateFragment.observe(this) { recreate() }
    }

    fun setVideoTheme() {
        if (viewModel.theme !is VideoTheme) return
        val player = SimpleExoPlayer.Builder(binding.themeVideo.context).build()
        val mediaItem =
            MediaItem.fromUri(Uri.parse(viewModel.theme.backgroundFile))
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

    private fun recreate() {
        val newInstance = ContactFragment()
        newInstance.arguments = arguments
        with(activityAs<MainActivity>()) {
            removeFragment(this@ContactFragment)
            addFragment(newInstance)
        }
    }

    private fun stopPlayer() {
        if (viewModel.theme is VideoTheme)
            binding.themeVideo.player?.apply { stop(); release() }
    }

    override fun onDestroy() {
        stopPlayer()
        super.onDestroy()
    }

    override fun provideViewModel() = viewModel

}