package com.galaxy.call.ui.contact

import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.galaxy.call.GAppG
import com.galaxy.call.R
import com.galaxy.call.base.GBaseGActivityG
import com.galaxy.call.base.GBaseGFragmentG
import com.galaxy.call.databinding.ContactFragmentBinding
import com.galaxy.call.model.contact.UserContact
import com.galaxy.call.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GContactGFragmentG : GBaseGFragmentG<GContactGViewGModelG, ContactFragmentBinding>(R.layout.contact_fragment) {

    val viewModel: GContactGViewGModelG by viewModel { parametersOf(contact, this) }

    private val contact: UserContact by lazy {
        println("")
        requireArguments().getSerializable(ARGUMENT_CONTACT) as UserContact
    }

    companion object {
        private const val ARGUMENT_CONTACT = "argument_contact"

        fun newInstance(contact: UserContact) = GContactGFragmentG().apply {
            println("")
            arguments = bundleOf(ARGUMENT_CONTACT to contact)
        }
    }

    override fun setupUI() {
        println("")
        Glide.with(GAppG.instance).load(viewModel.theme.previewFile).centerCrop().into(binding.preview)
        println("")
        if (contact.photoThumbnailUri !== null)
            Glide.with(GAppG.instance).load(contact.photoThumbnailUri).into(binding.avatar)
        println("")
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        println("")
        viewModel.callNumber.observe(this) { number ->
            println("")
            viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
                println("")
                if (it) activityAs<GBaseGActivityG<*, *>>().call(number)
                println("")
            }
            println("")
        }
        println("")
    }

    override fun provideViewModel() = viewModel

}