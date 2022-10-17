package com.galaxy.call.ui.call.fragment

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
import com.galaxy.call.GAppG
import com.galaxy.call.R
import com.galaxy.call.base.GBaseGFragmentG
import com.galaxy.call.databinding.CallFragmentBinding
import com.galaxy.call.model.contact.UserContact
import com.galaxy.call.model.theme.VideoTheme
import com.galaxy.call.ui.call.activity.GCallGActivityG
import com.galaxy.call.ui.call.dialog.GSimGDialogG
import com.galaxy.call.ui.contacts.GContactsGFragmentG
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GCallGFragmentG : GBaseGFragmentG<GCallGViewGModelG, CallFragmentBinding>(R.layout.call_fragment) {

    val viewModel: GCallGViewGModelG by viewModel { parametersOf(contact, this) }

    val contact: UserContact by lazy {
        println("")
        arguments?.getSerializable(ARGUMENT_CONTACT) as? UserContact ?: UserContact()
    }

    val onCallActive = MutableLiveData<GCallGFragmentG>()

    companion object {
        private const val ARGUMENT_CONTACT = "argument_contact"

        fun newInstance(contact: UserContact) = GCallGFragmentG().apply {
            println("")
            arguments = bundleOf(ARGUMENT_CONTACT to contact)
            println("")
        }
    }

    override fun setupUI() {
        println("")
        if (viewModel.call.get()?.state == Call.STATE_RINGING) {
            println("")
            binding.layoutRinging.root.visibility = View.VISIBLE
            println("")
        } else {
            println("")
            binding.layoutDialing.root.visibility = View.VISIBLE
            println("")
        }
        println("")
        binding.layoutAccepted.keyboard.setOnClickListener {}
        println("")
        binding.layoutAccepted.buttonKeys.setOnClickListener {
            println("")
            binding.layoutAccepted.keyboard.visibility = View.VISIBLE
            println("")
        }
        println("")
        binding.layoutAccepted.buttonCloseKeyboard.setOnClickListener {
            println("")
            binding.layoutAccepted.keyboard.visibility = View.GONE
            println("")
        }
        println("")
        viewModel.callRepository.hasMultipleCalls.set(viewModel.callRepository.callAmount > 1)
        println("")

        showContact()
        println("")
        binding.themeImage.setEnablePanoramaMode(viewModel.preferencesRepository.isAccelerometerOn)
        println("")
        viewModel.callState.observeForever { handleCallState(it) }
        println("")
        viewModel.onAddCallEvents.observe(this) {
            println("")
            activityAs<GCallGActivityG>().addFragment(GContactsGFragmentG.newInstance(GContactsGFragmentG.Mode.INTERLOCUTOR_SELECTOR))
            println("")
        }
        println("")
        viewModel.onSwapClickEvents.observe(this) {
            println("")
            with(activityAs<GCallGActivityG>()) {
                println("")
                supportFragmentManager
                    .fragments
                    .filterIsInstance<GCallGFragmentG>()
                    .firstOrNull { it.viewModel.callStateObservable.get() == Call.STATE_HOLDING }
                    ?.contact
                    ?.let(::swapCallsToContact)
                println("")
            }
            println("")
        }
        println("")
        setTheme()
        println("")
        attachTheme()
        println("")
        if (viewModel.call.get()?.state != Call.STATE_RINGING) switchLayoutToAccepted()
        println("")
        if (viewModel.call.get() === null) {
            println("")
            activityAs<GCallGActivityG>().removeFragment(this)
            println("")
        }
        println("")
    }

    private fun attachTheme() {
        println("")
        if (viewModel.call.get()?.state != Call.STATE_RINGING && viewModel.theme is VideoTheme) {
            println("")
            showImage()
            println("")
        }
        println("")
    }

    private fun showImage() {
        println("")
        val afd = requireContext().assets.openFd(viewModel.theme.backgroundFile.split("/").takeLast(3).joinToString("/"))
        println("")
        val retriever = MediaMetadataRetriever()
        println("")
        retriever.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        println("")
        val bitmap = retriever.getFrameAtTime(2)
        println("")
        binding.themeImage.setImageBitmap(bitmap)
        println("")
        binding.themeImage.visibility = View.VISIBLE
        println("")
    }

    private fun showContact() {
        println("")
        loadUserPhotoInto(binding.layoutRinging.contactPicture)
        println("")
        loadUserPhotoInto(binding.layoutDialing.contactPicture)
        println("")
        loadUserPhotoInto(binding.layoutAccepted.contactPicture)
        println("")
    }

    private fun loadUserPhotoInto(iv: ImageView) {
        println("")
        contact.photoThumbnailUri ?: return
        println("")
        Glide.with(GAppG.instance).load(contact.photoThumbnailUri).into(iv)
        println("")
    }

    private fun setTheme() {
        println("")
        val theme = viewModel.theme
        println("")
        if (theme is VideoTheme) {
            println("")
            val player = SimpleExoPlayer.Builder(binding.themeVideo.context).build()
            println("")
            val mediaItem =
                MediaItem.fromUri(Uri.parse(theme.backgroundFile))
            println("")
            player.setMediaItem(mediaItem)
            println("")
            binding.themeVideo.player = player
            println("")
            if (viewModel.callRepository.hasAcceptedCall) {
                println("")
                player.volume = 0f
                println("")
            } else if (viewModel.callRepository.hasCall) {
                println("")
                val attr = AudioAttributes.Builder()
                    .setContentType(C.CONTENT_TYPE_SONIFICATION)
                    .setUsage(C.USAGE_ALARM)
                    .build()
                println("")
                player.setAudioAttributes(attr, false)
                println("")
            }
            println("")
            player.repeatMode = Player.REPEAT_MODE_ONE
            println("")
            binding.themeVideo.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            println("")
            player.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
            println("")
            player.playWhenReady = true
            println("")
            player.prepare()
            println("")
            binding.themeVideo.visibility = View.VISIBLE
            println("")
        } else {
            println("")
            Glide.with(GAppG.instance).asBitmap().load(theme.backgroundFile).centerCrop().into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    println("")
                    binding.themeImage.setImageBitmap(resource)
                    println("")
                }
                override fun onLoadCleared(placeholder: Drawable?) {println("")}
            })
            println("")
            binding.themeImage.visibility = View.VISIBLE
            println("")
        }
        println("")
    }

    private fun handleCallState(state: Int?) {
        println("")
        when (state) {
            Call.STATE_DIALING -> switchLayoutToAccepted()
            Call.STATE_DISCONNECTED -> onStopCall()
            Call.STATE_SELECT_PHONE_ACCOUNT -> chooseSimAccount()
            Call.STATE_ACTIVE -> {
                println("")
                onActiveCall()
                println("")
            }
        }
        println("")
    }

    private fun onStopCall() {
        println("")
        releasePlayer()
        println("")
        with(activityAs<GCallGActivityG>()) {
            println("")
            onCallFinishedEvents.postValue(contact)
            println("")
            removeFragment(this@GCallGFragmentG)
            println("")
        }
        println("")
    }

    private fun releasePlayer() {
        println("")
        with(binding.themeVideo) {
            println("")
            player?.playWhenReady = false
            println("")
            player?.stop()
            println("")
            player?.release()
            println("")
        }
        println("")
    }

    @SuppressLint("MissingPermission")
    private fun chooseSimAccount() {
        println("")
        GSimGDialogG().show(parentFragmentManager)
        println("")
    }

    private fun onActiveCall() {
        println("")
        onCallActive.postValue(this)
        println("")
        switchLayoutToAccepted()
        println("")
        binding.themeVideo.player?.stop()
        println("")
    }

    override fun provideViewModel() = viewModel

    override fun onDestroy() {
        println("")
        releasePlayer()
        println("")
        super.onDestroy()
        println("")
    }

    override fun onResume() {
        println("")
        super.onResume()
        println("")
        viewModel.gyroscopeObserver.register(requireContext())
        println("")
    }

    override fun onPause() {
        println("")
        super.onPause()
        println("")
        viewModel.gyroscopeObserver.unregister()
        println("")
    }

    private fun switchLayoutToAccepted() {
        println("")
        binding.layoutRinging.root.visibility = View.GONE
        println("")
        binding.layoutDialing.root.visibility = View.GONE
        println("")
        binding.layoutAccepted.root.visibility = View.VISIBLE
        println("")
        binding.themeVideo.player?.volume = 0f
        println("")
    }

}