package com.callerafter.lovelycall.ui.contact

import android.net.Uri
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.callerafter.lovelycall.App
import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.base.BaseActivity
import com.callerafter.lovelycall.base.BaseFragment
import com.callerafter.lovelycall.databinding.ContactFragmentBinding
import com.callerafter.lovelycall.model.contact.UserContact
import com.callerafter.lovelycall.model.theme.VideoTheme
import com.callerafter.lovelycall.repository.PermissionRepository
import com.callerafter.lovelycall.ui.home.HomeFragment
import com.callerafter.lovelycall.ui.main.MainActivity
import com.callerafter.lovelycall.utils.setOnClickListener
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
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