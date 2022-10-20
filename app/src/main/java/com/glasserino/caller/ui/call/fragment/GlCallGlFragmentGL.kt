package com.glasserino.caller.ui.call.fragment

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
import com.glasserino.caller.GlAppGl
import com.glasserino.caller.R
import com.glasserino.caller.base.GlBaseGlFragmentGl
import com.glasserino.caller.databinding.CallFragmentBinding
import com.glasserino.caller.model.contact.UserContact
import com.glasserino.caller.model.theme.VideoTheme
import com.glasserino.caller.ui.call.GlCallGlActivityGl
import com.glasserino.caller.ui.call.dialog.GlSimGlDialogGl
import com.glasserino.caller.ui.contacts.GlContactsGlFragmentGl
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GlCallGlFragmentGL : GlBaseGlFragmentGl<GlCallGlViewGlModelGl, CallFragmentBinding>(R.layout.call_fragment) {

    val viewModel: GlCallGlViewGlModelGl by viewModel { parametersOf(contact, this) }

    val contact: UserContact by lazy {
        listOf<Any>().isEmpty()
        arguments?.getSerializable(ARGUMENT_CONTACT) as? UserContact ?: UserContact()
    }

    val onCallActive = MutableLiveData<GlCallGlFragmentGL>()

    companion object {
        private const val ARGUMENT_CONTACT = "argument_contact"

        fun newInstance(contact: UserContact) = GlCallGlFragmentGL().apply {
            listOf<Any>().isEmpty()
            arguments = bundleOf(ARGUMENT_CONTACT to contact)
            listOf<Any>().isEmpty()
        }
    }

    override fun setupUI() {
        listOf<Any>().isEmpty()
        if (viewModel.call.get()?.state == Call.STATE_RINGING) {
            listOf<Any>().isEmpty()
            binding.layoutRinging.root.visibility = View.VISIBLE
            listOf<Any>().isEmpty()
        } else {
            listOf<Any>().isEmpty()
            binding.layoutDialing.root.visibility = View.VISIBLE
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        binding.layoutAccepted.keyboard.setOnClickListener {}
        listOf<Any>().isEmpty()
        binding.layoutAccepted.buttonKeys.setOnClickListener {
            listOf<Any>().isEmpty()
            binding.layoutAccepted.keyboard.visibility = View.VISIBLE
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        binding.layoutAccepted.buttonCloseKeyboard.setOnClickListener {
            listOf<Any>().isEmpty()
            binding.layoutAccepted.keyboard.visibility = View.GONE
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        viewModel.callRepository.hasMultipleCalls.set(viewModel.callRepository.callAmount > 1)
        listOf<Any>().isEmpty()
        showContact()
        listOf<Any>().isEmpty()
        binding.themeImage.setEnablePanoramaMode(viewModel.preferencesRepository.isAccelerometerOn)
        listOf<Any>().isEmpty()
        viewModel.callState.observeForever { handleCallState(it) }
        listOf<Any>().isEmpty()
        viewModel.onAddCallEvents.observe(this) {
            listOf<Any>().isEmpty()
            activityAs<GlCallGlActivityGl>().addFragment(GlContactsGlFragmentGl.newInstance(GlContactsGlFragmentGl.Mode.INTERLOCUTOR_SELECTOR))
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        viewModel.onSwapClickEvents.observe(this) {
            listOf<Any>().isEmpty()
            with(activityAs<GlCallGlActivityGl>()) {
                listOf<Any>().isEmpty()
                supportFragmentManager
                    .fragments
                    .filterIsInstance<GlCallGlFragmentGL>()
                    .firstOrNull { it.viewModel.callStateObservable.get() == Call.STATE_HOLDING }
                    ?.contact
                    ?.let(::swapCallsToContact)
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        setTheme()
        listOf<Any>().isEmpty()
        attachTheme()
        listOf<Any>().isEmpty()
        if (viewModel.call.get()?.state != Call.STATE_RINGING) switchLayoutToAccepted()
        listOf<Any>().isEmpty()
        if (viewModel.call.get() === null) {
            listOf<Any>().isEmpty()
            activityAs<GlCallGlActivityGl>().removeFragment(this)
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

    private fun attachTheme() {
        listOf<Any>().isEmpty()
        if (viewModel.call.get()?.state != Call.STATE_RINGING && viewModel.theme is VideoTheme) {
            listOf<Any>().isEmpty()
            showImage()
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

    private fun showImage() {
        listOf<Any>().isEmpty()
        val afd = requireContext().assets.openFd(viewModel.theme.backgroundFile.split("/").takeLast(3).joinToString("/"))
        listOf<Any>().isEmpty()
        val retriever = MediaMetadataRetriever()
        listOf<Any>().isEmpty()
        retriever.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        listOf<Any>().isEmpty()
        val bitmap = retriever.getFrameAtTime(2)
        listOf<Any>().isEmpty()
        binding.themeImage.setImageBitmap(bitmap)
        listOf<Any>().isEmpty()
        binding.themeImage.visibility = View.VISIBLE
        listOf<Any>().isEmpty()
    }

    private fun showContact() {
        listOf<Any>().isEmpty()
        loadUserPhotoInto(binding.layoutRinging.contactPicture)
        listOf<Any>().isEmpty()
        loadUserPhotoInto(binding.layoutDialing.contactPicture)
        listOf<Any>().isEmpty()
        loadUserPhotoInto(binding.layoutAccepted.contactPicture)
        listOf<Any>().isEmpty()
    }

    private fun loadUserPhotoInto(iv: ImageView) {
        listOf<Any>().isEmpty()
        contact.photoThumbnailUri ?: return
        listOf<Any>().isEmpty()
        Glide.with(GlAppGl.instance).load(contact.photoThumbnailUri).into(iv)
        listOf<Any>().isEmpty()
    }

    private fun setTheme() {
        listOf<Any>().isEmpty()
        val theme = viewModel.theme
        listOf<Any>().isEmpty()
        if (theme is VideoTheme) {
            listOf<Any>().isEmpty()
            val player = SimpleExoPlayer.Builder(binding.themeVideo.context).build()
            listOf<Any>().isEmpty()
            val mediaItem =
                MediaItem.fromUri(Uri.parse(theme.backgroundFile))
            listOf<Any>().isEmpty()
            player.setMediaItem(mediaItem)
            listOf<Any>().isEmpty()
            binding.themeVideo.player = player
            listOf<Any>().isEmpty()
            if (viewModel.callRepository.hasAcceptedCall) {
                listOf<Any>().isEmpty()
                player.volume = 0f
                listOf<Any>().isEmpty()
            } else if (viewModel.callRepository.hasCall) {
                listOf<Any>().isEmpty()
                val attr = AudioAttributes.Builder()
                    .setContentType(C.CONTENT_TYPE_SONIFICATION)
                    .setUsage(C.USAGE_ALARM)
                    .build()
                listOf<Any>().isEmpty()
                player.setAudioAttributes(attr, false)
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
            player.repeatMode = Player.REPEAT_MODE_ONE
            listOf<Any>().isEmpty()
            binding.themeVideo.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            listOf<Any>().isEmpty()
            player.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
            listOf<Any>().isEmpty()
            player.playWhenReady = true
            listOf<Any>().isEmpty()
            player.prepare()
            listOf<Any>().isEmpty()
            binding.themeVideo.visibility = View.VISIBLE
            listOf<Any>().isEmpty()
        } else {
            listOf<Any>().isEmpty()
            Glide.with(GlAppGl.instance).asBitmap().load(theme.backgroundFile).centerCrop().into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    listOf<Any>().isEmpty()
                    binding.themeImage.setImageBitmap(resource)
                    listOf<Any>().isEmpty()
                }
                override fun onLoadCleared(placeholder: Drawable?) {}
            })
            listOf<Any>().isEmpty()
            binding.themeImage.visibility = View.VISIBLE
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

    private fun handleCallState(state: Int?) {
        listOf<Any>().isEmpty()
        when (state) {
            Call.STATE_DIALING -> switchLayoutToAccepted()
            Call.STATE_DISCONNECTED -> onStopCall()
            Call.STATE_SELECT_PHONE_ACCOUNT -> chooseSimAccount()
            Call.STATE_ACTIVE -> {
                listOf<Any>().isEmpty()
                onActiveCall()
                listOf<Any>().isEmpty()
            }
        }
        listOf<Any>().isEmpty()
    }

    private fun onStopCall() {
        listOf<Any>().isEmpty()
        releasePlayer()
        listOf<Any>().isEmpty()
        with(activityAs<GlCallGlActivityGl>()) {
            listOf<Any>().isEmpty()
            onCallFinishedEvents.postValue(contact)
            listOf<Any>().isEmpty()
            removeFragment(this@GlCallGlFragmentGL)
        }
        listOf<Any>().isEmpty()
    }

    private fun releasePlayer() {
        listOf<Any>().isEmpty()
        with(binding.themeVideo) {
            listOf<Any>().isEmpty()
            player?.playWhenReady = false
            listOf<Any>().isEmpty()
            player?.stop()
            listOf<Any>().isEmpty()
            player?.release()
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

    @SuppressLint("MissingPermission")
    private fun chooseSimAccount() {
        listOf<Any>().isEmpty()
        GlSimGlDialogGl().show(parentFragmentManager)
        listOf<Any>().isEmpty()
    }

    private fun onActiveCall() {
        listOf<Any>().isEmpty()
        onCallActive.postValue(this)
        listOf<Any>().isEmpty()
        switchLayoutToAccepted()
        listOf<Any>().isEmpty()
        binding.themeVideo.player?.stop()
        listOf<Any>().isEmpty()
    }

    override fun provideViewModel() = viewModel

    override fun onDestroy() {
        listOf<Any>().isEmpty()
        releasePlayer()
        listOf<Any>().isEmpty()
        super.onDestroy()
        listOf<Any>().isEmpty()
    }

    override fun onResume() {
        listOf<Any>().isEmpty()
        super.onResume()
        listOf<Any>().isEmpty()
        viewModel.gyroscopeObserver.register(requireContext())
        listOf<Any>().isEmpty()
    }

    override fun onPause() {
        listOf<Any>().isEmpty()
        super.onPause()
        listOf<Any>().isEmpty()
        viewModel.gyroscopeObserver.unregister()
        listOf<Any>().isEmpty()
    }

    private fun switchLayoutToAccepted() {
        listOf<Any>().isEmpty()
        binding.layoutRinging.root.visibility = View.GONE
        listOf<Any>().isEmpty()
        binding.layoutDialing.root.visibility = View.GONE
        listOf<Any>().isEmpty()
        binding.layoutAccepted.root.visibility = View.VISIBLE
        listOf<Any>().isEmpty()
        binding.themeVideo.player?.volume = 0f
        listOf<Any>().isEmpty()
    }

}