package com.callerafter.lovelycall.ui.call.fragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.SystemClock
import android.telecom.Call
import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.callerafter.lovelycall.App
import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.base.BaseFragment
import com.callerafter.lovelycall.databinding.CallFragmentBinding
import com.callerafter.lovelycall.model.contact.UserContact
import com.callerafter.lovelycall.model.theme.VideoTheme
import com.callerafter.lovelycall.ui.call.CallActivity
import com.callerafter.lovelycall.ui.call.dialog.ActiveCallDialog
import com.callerafter.lovelycall.ui.call.dialog.SimDialog
import com.callerafter.lovelycall.ui.call.dialog.SpeakerTypeDialog
import com.callerafter.lovelycall.ui.contacts.ContactsFragment
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CallFragment : BaseFragment<CallViewModel, CallFragmentBinding>(R.layout.call_fragment) {

    val viewModel: CallViewModel by viewModel { parametersOf(contact) }

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
        binding.layoutAccepted.callKeyboard.binding.viewModel = viewModel
        viewModel.callRepository.hasMultipleCalls.set(viewModel.callRepository.callAmount > 1)
        binding.keyboard.binding.buttonCall.visibility = View.VISIBLE

        showContact()

        binding.themeImage.setEnablePanoramaMode(viewModel.preferencesRepository.isAccelerometerOn)

        binding.keyboard.setOnClickListener {}
        binding.keyboard.setOnClickListener {

        }
        viewModel.callState.observeForever { handleCallState(it) }
        binding.layoutAccepted.callKeyboard.binding.kKeypad.root.setOnClickListener {
            binding.keyboard.visibility = View.VISIBLE
        }
        binding.keyboard.binding.buttonClose.setOnClickListener {
            binding.keyboard.visibility = View.GONE
        }

        viewModel.onDialogOpenListener.observe(this) { SpeakerTypeDialog().show(parentFragmentManager) }
        viewModel.onAddCallEvents.observe(this) {
            activityAs<CallActivity>().addFragment(ContactsFragment.newInstance(ContactsFragment.Mode.INTERLOCUTOR_SELECTOR))
        }
        viewModel.onSwapClickEvents.observe(this) {
            with(activityAs<CallActivity>()) {
                callSwapDialog = ActiveCallDialog()
                callSwapDialog?.show(parentFragmentManager)
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
        loadUserPhotoInto(binding.layoutNotAccepted.thumbnail)
        loadUserPhotoInto(binding.layoutAccepted.thumbnail)
    }

    private fun loadUserPhotoInto(iv: ImageView) {
        Glide.with(App.instance).let {
            if (contact.photoThumbnailUri === null) it.load(R.mipmap.ic_no_logo)
            else it.load(contact.photoThumbnailUri)
        }.into(iv)
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
                startChronometer()
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

    private fun startChronometer() {
        if (isChronometerStarted) return
        binding.layoutAccepted.chronometer.base = SystemClock.elapsedRealtime()
        binding.layoutAccepted.chronometer.start()
        isChronometerStarted = true
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
        binding.layoutNotAccepted.root.visibility = View.GONE
        binding.layoutAccepted.root.visibility = View.VISIBLE
    }

}