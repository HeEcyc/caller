package com.megaaa.caaall.ui.call.dialog

import com.megaaa.caaall.R
import com.megaaa.caaall.base.BaseActivity
import com.megaaa.caaall.base.BaseDialog
import com.megaaa.caaall.databinding.SpeakerTypeDialogBinding
import com.megaaa.caaall.repository.call.AudioManagerRepository
import com.megaaa.caaall.repository.call.AudioManagerRepository.CallType.*
import com.megaaa.caaall.ui.call.fragment.CallFragment
import com.megaaa.caaall.utils.setOnClickListener

class SpeakerTypeDialog : BaseDialog<SpeakerTypeDialogBinding>(R.layout.speaker_type_dialog) {

    private val speakerType by lazy {
        activityAs<BaseActivity<*, *>>()
            .fragment(CallFragment::class.java)
            ?.viewModel
            ?.callRepository
            ?.audioManagerRepository
            ?.currentCallType
    }

    override fun setupUI() {
        when (speakerType) {
            BLUETOOTH -> binding.radioBluetooth
            PHONE -> binding.radioPhone
            SPEAKER -> binding.radioPublicSpeaker
            else -> { dismiss(); return }
        }.apply { setImageResource(R.drawable.radio_button_selected) }
        binding.buttonCancel.setOnClickListener(::dismiss)
        binding.buttonBluetooth.setOnClickListener { onTypeSelected(BLUETOOTH) }
        binding.buttonPhone.setOnClickListener { onTypeSelected(PHONE) }
        binding.buttonPublicSpeaker.setOnClickListener { onTypeSelected(SPEAKER) }
    }

    private fun onTypeSelected(type: AudioManagerRepository.CallType) {
        if (type == speakerType) return
        with(activityAs<BaseActivity<*, *>>().fragment(CallFragment::class.java)?.viewModel) {
            when (type) {
                BLUETOOTH -> this?.setBluetoothCallType()
                PHONE -> this?.setSpeakerCallType()
                SPEAKER -> this?.setSpeakerType()
            }
        }
        dismiss()
    }

}