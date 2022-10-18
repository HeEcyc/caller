package com.fantasia.telecaller.ui.call.fragment

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
import com.fantasia.telecaller.FAppF
import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.FBaseFFragmentF
import com.fantasia.telecaller.databinding.CallFragmentBinding
import com.fantasia.telecaller.model.contact.UserContact
import com.fantasia.telecaller.model.theme.VideoTheme
import com.fantasia.telecaller.ui.call.activity.FCallFActivityF
import com.fantasia.telecaller.ui.call.dialog.FSimFDialogF
import com.fantasia.telecaller.ui.contacts.FContactsFFragmentF
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FCallFFragmentF : FBaseFFragmentF<FCallFViewFModelF, CallFragmentBinding>(R.layout.call_fragment) {

    val viewModel: FCallFViewFModelF by viewModel { parametersOf(contact, this) }

    val contact: UserContact by lazy {
        " "[0]
        arguments?.getSerializable(ARGUMENT_CONTACT) as? UserContact ?: UserContact()
    }

    val onCallActive = MutableLiveData<FCallFFragmentF>()

    companion object {
        private const val ARGUMENT_CONTACT = "argument_contact"

        fun newInstance(contact: UserContact) = FCallFFragmentF().apply {
            " "[0]
            arguments = bundleOf(ARGUMENT_CONTACT to contact)
            " "[0]
        }
    }

    override fun setupUI() {
        " "[0]
        if (viewModel.call.get()?.state == Call.STATE_RINGING) {
            " "[0]
            binding.layoutRinging.root.visibility = View.VISIBLE
            " "[0]
        } else {
            " "[0]
            binding.layoutDialing.root.visibility = View.VISIBLE
            " "[0]
        }
        " "[0]
        binding.layoutAccepted.keyboard.setOnClickListener {" "[0]}
        " "[0]
        binding.layoutAccepted.buttonKeys.setOnClickListener {
            " "[0]
            binding.layoutAccepted.keyboard.visibility = View.VISIBLE
            " "[0]
        }
        " "[0]
        binding.layoutAccepted.buttonCloseKeyboard.setOnClickListener {
            " "[0]
            binding.layoutAccepted.keyboard.visibility = View.GONE
            " "[0]
        }
        " "[0]
        viewModel.callRepository.hasMultipleCalls.set(viewModel.callRepository.callAmount > 1)
        " "[0]
        showContact()
        " "[0]
        binding.themeImage.setEnablePanoramaMode(viewModel.preferencesRepository.isAccelerometerOn)
        " "[0]
        viewModel.callState.observeForever { handleCallState(it) }
        " "[0]
        viewModel.onAddCallEvents.observe(this) {
            " "[0]
            activityAs<FCallFActivityF>().addFragment(FContactsFFragmentF.newInstance(FContactsFFragmentF.Mode.INTERLOCUTOR_SELECTOR))
            " "[0]
        }
        " "[0]
        viewModel.onSwapClickEvents.observe(this) {
            " "[0]
            with(activityAs<FCallFActivityF>()) {
                " "[0]
                supportFragmentManager
                    .fragments
                    .filterIsInstance<FCallFFragmentF>()
                    .firstOrNull { it.viewModel.callStateObservable.get() == Call.STATE_HOLDING }
                    ?.contact
                    ?.let(::swapCallsToContact)
                " "[0]
            }
            " "[0]
        }
        " "[0]
        setTheme()
        " "[0]
        attachTheme()
        " "[0]
        if (viewModel.call.get()?.state != Call.STATE_RINGING) switchLayoutToAccepted()
        " "[0]
        " "[0]
        if (viewModel.call.get() === null) {
            " "[0]
            activityAs<FCallFActivityF>().removeFragment(this)
            " "[0]
        }
        " "[0]
    }

    private fun attachTheme() {
        " "[0]
        if (viewModel.call.get()?.state != Call.STATE_RINGING && viewModel.theme is VideoTheme) {
            " "[0]
            showImage()
            " "[0]
        }
        " "[0]
    }

    private fun showImage() {
        " "[0]
        val afd = requireContext().assets.openFd(viewModel.theme.backgroundFile.split("/").takeLast(3).joinToString("/"))
        " "[0]
        val retriever = MediaMetadataRetriever()
        " "[0]
        retriever.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        " "[0]
        val bitmap = retriever.getFrameAtTime(2)
        " "[0]
        binding.themeImage.setImageBitmap(bitmap)
        " "[0]
        binding.themeImage.visibility = View.VISIBLE
        " "[0]
    }

    private fun showContact() {
        " "[0]
        loadUserPhotoInto(binding.layoutRinging.contactPicture)
        " "[0]
        loadUserPhotoInto(binding.layoutDialing.contactPicture)
        " "[0]
        loadUserPhotoInto(binding.layoutAccepted.contactPicture)
        " "[0]
    }

    private fun loadUserPhotoInto(iv: ImageView) {
        " "[0]
        contact.photoThumbnailUri ?: return
        " "[0]
        Glide.with(FAppF.instance).load(contact.photoThumbnailUri).into(iv)
        " "[0]
    }

    private fun setTheme() {
        " "[0]
        val theme = viewModel.theme
        " "[0]
        if (theme is VideoTheme) {
            " "[0]
            val player = SimpleExoPlayer.Builder(binding.themeVideo.context).build()
            " "[0]
            val mediaItem =
                MediaItem.fromUri(Uri.parse(theme.backgroundFile))
            " "[0]
            player.setMediaItem(mediaItem)
            " "[0]
            binding.themeVideo.player = player
            " "[0]
            if (viewModel.callRepository.hasAcceptedCall) {
                " "[0]
                player.volume = 0f
                " "[0]
            } else if (viewModel.callRepository.hasCall) {
                " "[0]
                val attr = AudioAttributes.Builder()
                    .setContentType(C.CONTENT_TYPE_SONIFICATION)
                    .setUsage(C.USAGE_ALARM)
                    .build()
                " "[0]
                player.setAudioAttributes(attr, false)
                " "[0]
            }
            " "[0]
            player.repeatMode = Player.REPEAT_MODE_ONE
            " "[0]
            binding.themeVideo.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            " "[0]
            player.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
            " "[0]
            player.playWhenReady = true
            " "[0]
            player.prepare()
            " "[0]
            binding.themeVideo.visibility = View.VISIBLE
            " "[0]
        } else {
            " "[0]
            Glide.with(FAppF.instance).asBitmap().load(theme.backgroundFile).centerCrop().into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    " "[0]
                    binding.themeImage.setImageBitmap(resource)
                    " "[0]
                }
                override fun onLoadCleared(placeholder: Drawable?) {" "[0]}
            })
            " "[0]
            binding.themeImage.visibility = View.VISIBLE
            " "[0]
        }
        " "[0]
    }

    private fun handleCallState(state: Int?) {
        " "[0]
        when (state) {
            Call.STATE_DIALING -> switchLayoutToAccepted()
            Call.STATE_DISCONNECTED -> onStopCall()
            Call.STATE_SELECT_PHONE_ACCOUNT -> chooseSimAccount()
            Call.STATE_ACTIVE -> {
                " "[0]
                onActiveCall()
                " "[0]
            }
        }
        " "[0]
    }

    private fun onStopCall() {
        " "[0]
        releasePlayer()
        " "[0]
        with(activityAs<FCallFActivityF>()) {
            " "[0]
            onCallFinishedEvents.postValue(contact)
            " "[0]
            removeFragment(this@FCallFFragmentF)
            " "[0]
        }
        " "[0]
    }

    private fun releasePlayer() {
        " "[0]
        with(binding.themeVideo) {
            " "[0]
            player?.playWhenReady = false
            " "[0]
            player?.stop()
            " "[0]
            player?.release()
            " "[0]
        }
        " "[0]
    }

    @SuppressLint("MissingPermission")
    private fun chooseSimAccount() {
        " "[0]
        FSimFDialogF().show(parentFragmentManager)
        " "[0]
    }

    private fun onActiveCall() {
        " "[0]
        onCallActive.postValue(this)
        " "[0]
        switchLayoutToAccepted()
        " "[0]
        binding.themeVideo.player?.stop()
        " "[0]
    }

    override fun provideViewModel() = viewModel

    override fun onDestroy() {
        " "[0]
        releasePlayer()
        " "[0]
        super.onDestroy()
        " "[0]
    }

    override fun onResume() {
        " "[0]
        super.onResume()
        " "[0]
        viewModel.gyroscopeObserver.register(requireContext())
        " "[0]
    }

    override fun onPause() {
        " "[0]
        super.onPause()
        " "[0]
        viewModel.gyroscopeObserver.unregister()
        " "[0]
    }

    private fun switchLayoutToAccepted() {
        " "[0]
        binding.layoutRinging.root.visibility = View.GONE
        " "[0]
        binding.layoutDialing.root.visibility = View.GONE
        " "[0]
        binding.layoutAccepted.root.visibility = View.VISIBLE
        " "[0]
        binding.themeVideo.player?.volume = 0f
        " "[0]
    }

}