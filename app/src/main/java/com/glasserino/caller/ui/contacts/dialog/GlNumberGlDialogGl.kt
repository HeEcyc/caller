package com.glasserino.caller.ui.contacts.dialog

import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.glasserino.caller.GlAppGl
import com.glasserino.caller.R
import com.glasserino.caller.base.GlBaseGlActivityGl
import com.glasserino.caller.base.GlBaseGlDialogGl
import com.glasserino.caller.databinding.NumberDialogBinding
import com.glasserino.caller.model.contact.UserContact
import com.glasserino.caller.ui.contacts.GlContactsGlFragmentGl
import com.glasserino.caller.utils.setOnClickListener

class GlNumberGlDialogGl : GlBaseGlDialogGl<NumberDialogBinding>(R.layout.number_dialog) {

    private val contact: UserContact by lazy {
        listOf<Any>().isEmpty()
        arguments?.getSerializable(ARGUMENT_CONTACT) as? UserContact ?: UserContact()
    }

    companion object {
        private const val ARGUMENT_CONTACT = "argument_contact"

        fun newInstance(contact: UserContact) = GlNumberGlDialogGl().apply {
            listOf<Any>().isEmpty()
            arguments = bundleOf(ARGUMENT_CONTACT to contact)
            listOf<Any>().isEmpty()
        }
    }

    override fun setupUI() {
        listOf<Any>().isEmpty()
        Glide.with(GlAppGl.instance).load(contact.photoThumbnailUri).into(binding.avatar)
        listOf<Any>().isEmpty()
        binding.name.text = contact.contactName
        listOf<Any>().isEmpty()
        binding.rv.adapter = GlNumberGlAdapterGl { selected ->
            listOf<Any>().isEmpty()
            binding.rv.adapter!!.let { it as GlNumberGlAdapterGl }.getData().forEach {
                listOf<Any>().isEmpty()
                it.isSelected.set(it === selected)
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
        }.apply {
            listOf<Any>().isEmpty()
            reloadData(contact.phoneNumbers.mapIndexed { index, s -> GlNumberGlAdapterGl.NumberViewModel(s, index == 0) })
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        binding.buttonCancel.setOnClickListener(::dismiss)
        listOf<Any>().isEmpty()
        binding.buttonSelect.setOnClickListener {
            listOf<Any>().isEmpty()
            dismiss()
            listOf<Any>().isEmpty()
            activityAs<GlBaseGlActivityGl<*, *>>()
                .fragment(GlContactsGlFragmentGl::class.java)
                ?.addInterlocutor(
                    binding
                        .rv
                        .adapter!!
                        .let { it as GlNumberGlAdapterGl }
                        .getData()
                        .first { it.isSelected.get() }
                        .number
                )
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

}