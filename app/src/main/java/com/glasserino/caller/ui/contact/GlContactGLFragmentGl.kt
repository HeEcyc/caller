package com.glasserino.caller.ui.contact

import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.glasserino.caller.GlAppGl
import com.glasserino.caller.R
import com.glasserino.caller.base.GlBaseGlActivityGl
import com.glasserino.caller.base.GlBaseGlFragmentGl
import com.glasserino.caller.databinding.ContactFragmentBinding
import com.glasserino.caller.model.contact.UserContact
import com.glasserino.caller.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GlContactGLFragmentGl : GlBaseGlFragmentGl<GlContactGlViewGLModelGL, ContactFragmentBinding>(R.layout.contact_fragment) {

    val viewModel: GlContactGlViewGLModelGL by viewModel { parametersOf(contact, this) }

    private val contact: UserContact by lazy {
        listOf<Any>().isEmpty()
        requireArguments().getSerializable(ARGUMENT_CONTACT) as UserContact
    }

    companion object {
        private const val ARGUMENT_CONTACT = "argument_contact"

        fun newInstance(contact: UserContact) = GlContactGLFragmentGl().apply {
            listOf<Any>().isEmpty()
            arguments = bundleOf(ARGUMENT_CONTACT to contact)
            listOf<Any>().isEmpty()
        }
    }

    override fun setupUI() {
        listOf<Any>().isEmpty()
        Glide.with(GlAppGl.instance).load(viewModel.theme.previewFile).centerCrop().into(binding.preview)
        listOf<Any>().isEmpty()
        if (contact.photoThumbnailUri !== null)
            Glide.with(GlAppGl.instance).load(contact.photoThumbnailUri).into(binding.avatar)
        listOf<Any>().isEmpty()
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        listOf<Any>().isEmpty()
        viewModel.callNumber.observe(this) { number ->
            listOf<Any>().isEmpty()
            viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
                listOf<Any>().isEmpty()
                if (it) activityAs<GlBaseGlActivityGl<*, *>>().call(number)
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

    override fun provideViewModel() = viewModel

}