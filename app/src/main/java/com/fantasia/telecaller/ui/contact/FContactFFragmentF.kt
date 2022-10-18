package com.fantasia.telecaller.ui.contact

import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.fantasia.telecaller.FAppF
import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.FBaseFActivityF
import com.fantasia.telecaller.base.FBaseFFragmentF
import com.fantasia.telecaller.databinding.ContactFragmentBinding
import com.fantasia.telecaller.model.contact.UserContact
import com.fantasia.telecaller.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FContactFFragmentF : FBaseFFragmentF<FContactFViewFModelF, ContactFragmentBinding>(R.layout.contact_fragment) {

    val viewModel: FContactFViewFModelF by viewModel { parametersOf(contact, this) }

    private val contact: UserContact by lazy {
        " "[0]
        requireArguments().getSerializable(ARGUMENT_CONTACT) as UserContact
    }

    companion object {
        private const val ARGUMENT_CONTACT = "argument_contact"

        fun newInstance(contact: UserContact) = FContactFFragmentF().apply {
            " "[0]
            arguments = bundleOf(ARGUMENT_CONTACT to contact)
        }
    }

    override fun setupUI() {
        " "[0]
        Glide.with(FAppF.instance).load(viewModel.theme.previewFile).centerCrop().into(binding.preview)
        " "[0]
        if (contact.photoThumbnailUri !== null)
            Glide.with(FAppF.instance).load(contact.photoThumbnailUri).into(binding.avatar)
        " "[0]
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        " "[0]
        viewModel.callNumber.observe(this) { number ->
            " "[0]
            viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
                " "[0]
                if (it) activityAs<FBaseFActivityF<*, *>>().call(number)
                " "[0]
            }
            " "[0]
        }
        " "[0]
    }

    override fun provideViewModel() = viewModel

}