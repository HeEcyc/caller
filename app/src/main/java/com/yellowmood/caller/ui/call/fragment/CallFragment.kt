package com.yellowmood.caller.ui.call.fragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.telecom.Call
import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.yellowmood.caller.App
import com.yellowmood.caller.R
import com.yellowmood.caller.base.BaseFragment
import com.yellowmood.caller.databinding.CallFragmentBinding
import com.yellowmood.caller.model.contact.UserContact
import com.yellowmood.caller.model.theme.VideoTheme
import com.yellowmood.caller.ui.call.activity.CallActivity
import com.yellowmood.caller.ui.call.dialog.SimDialog
import com.yellowmood.caller.ui.contacts.ContactsFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CallFragment : BaseFragment<CallViewModel, CallFragmentBinding>(R.layout.call_fragment) {

    val viewModel: CallViewModel by viewModel { parametersOf(contact, this) }

    val contact: UserContact by lazy {
        arguments?.getSerializable(ARGUMENT_CONTACT) as? UserContact ?: UserContact()
    }

    val onCallActive = MutableLiveData<CallFragment>()

    private var isChronometerStarted = false

    companion object {
        private const val ARGUMENT_CONTACT = "argument_contact"

        fun newInstance(contact: UserContact) = CallFragment().apply {
            arguments = bundleOf(ARGUMENT_CONTACT to contact)
        }
    }

    override fun setupUI() {
        if (viewModel.call.get()?.state == Call.STATE_RINGING) {
            binding.layoutRinging.root.visibility = View.VISIBLE
        } else {
            binding.layoutDialing.root.visibility = View.VISIBLE
        }
        binding.layoutAccepted.keyboard.setOnClickListener {}
        binding.layoutAccepted.buttonKeys.setOnClickListener {
            binding.layoutAccepted.keyboard.visibility = View.VISIBLE
        }
        binding.layoutAccepted.buttonCloseKeyboard.setOnClickListener {
            binding.layoutAccepted.keyboard.visibility = View.GONE
        }
        viewModel.callRepository.hasMultipleCalls.set(viewModel.callRepository.callAmount > 1)

        showContact()

        binding.themeImage.setEnablePanoramaMode(viewModel.preferencesRepository.isAccelerometerOn)

        viewModel.callState.observeForever { handleCallState(it) }

        viewModel.onAddCallEvents.observe(this) {
            activityAs<CallActivity>().addFragment(ContactsFragment.newInstance(ContactsFragment.Mode.INTERLOCUTOR_SELECTOR))
        }
        viewModel.onSwapClickEvents.observe(this) {
            with(activityAs<CallActivity>()) {
                supportFragmentManager
                    .fragments
                    .filterIsInstance<CallFragment>()
                    .firstOrNull { it.viewModel.callStateObservable.get() == Call.STATE_HOLDING }
                    ?.contact
                    ?.let(::swapCallsToContact)
            }
        }

        setTheme()
        attachTheme()
        if (viewModel.call.get()?.state != Call.STATE_RINGING) switchLayoutToAccepted()

        if (viewModel.call.get() === null) {
            activityAs<CallActivity>().removeFragment(this)
        }
    }

    private fun attachTheme() {
        if (viewModel.call.get()?.state != Call.STATE_RINGING && viewModel.theme is VideoTheme) {
            showImage()
        }
    }

    private fun showImage() {
        val afd = requireContext().assets.openFd(viewModel.theme.backgroundFile.split("/").takeLast(3).joinToString("/"))
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        val bitmap = retriever.getFrameAtTime(2)
        binding.themeImage.setImageBitmap(bitmap)
        binding.themeImage.visibility = View.VISIBLE
    }

    private fun showContact() {
        loadUserPhotoInto(binding.layoutRinging.contactPicture)
        loadUserPhotoInto(binding.layoutDialing.contactPicture)
        loadUserPhotoInto(binding.layoutAccepted.contactPicture)
    }

    private fun loadUserPhotoInto(iv: ImageView) {
        contact.photoThumbnailUri ?: return
        Glide.with(App.instance).load(contact.photoThumbnailUri).into(iv)
    }

    private fun setTheme() {
        val theme = viewModel.theme
        if (theme is VideoTheme) {
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
                    .setUsage(C.USAGE_ALARM)
                    .build()
                player.setAudioAttributes(attr, false)
            }
            player.repeatMode = Player.REPEAT_MODE_ONE
            binding.themeVideo.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            player.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
            player.playWhenReady = true
            player.prepare()
            binding.themeVideo.visibility = View.VISIBLE
        } else {
            Glide.with(App.instance).asBitmap().load(theme.backgroundFile).centerCrop().into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    binding.themeImage.setImageBitmap(resource)
                }
                override fun onLoadCleared(placeholder: Drawable?) {}
            })
            binding.themeImage.visibility = View.VISIBLE
        }
    }

    private fun handleCallState(state: Int?) {
        when (state) {
            Call.STATE_DIALING -> switchLayoutToAccepted()
            Call.STATE_DISCONNECTED -> onStopCall()
            Call.STATE_SELECT_PHONE_ACCOUNT -> chooseSimAccount()
            Call.STATE_ACTIVE -> {
                onActiveCall()
            }
        }
    }

    private fun onStopCall() {
        releasePlayer()
        with(activityAs<CallActivity>()) {
            onCallFinishedEvents.postValue(contact)
            removeFragment(this@CallFragment)
        }
    }

    private fun releasePlayer() {
        with(binding.themeVideo) {
            player?.playWhenReady = false
            player?.stop()
            player?.release()
        }
    }

    @SuppressLint("MissingPermission")
    private fun chooseSimAccount() {
        SimDialog().show(parentFragmentManager)
    }

    private fun onActiveCall() {
        onCallActive.postValue(this)
        switchLayoutToAccepted()
        binding.themeVideo.player?.stop()
    }

    override fun provideViewModel() = viewModel

    override fun onDestroy() {
        releasePlayer()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        viewModel.gyroscopeObserver.register(requireContext())
    }

    override fun onPause() {
        super.onPause()
        viewModel.gyroscopeObserver.unregister()
    }

    private fun switchLayoutToAccepted() {
        binding.layoutRinging.root.visibility = View.GONE
        binding.layoutDialing.root.visibility = View.GONE
        binding.layoutAccepted.root.visibility = View.VISIBLE
        binding.themeVideo.player?.volume = 0f
    }

}