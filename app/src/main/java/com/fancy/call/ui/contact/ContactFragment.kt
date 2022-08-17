package com.fancy.call.ui.contact

import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.fancy.call.App
import com.fancy.call.R
import com.fancy.call.base.BaseActivity
import com.fancy.call.base.BaseFragment
import com.fancy.call.databinding.ContactFragmentBinding
import com.fancy.call.model.contact.UserContact
import com.fancy.call.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ContactFragment : BaseFragment<ContactViewModel, ContactFragmentBinding>(R.layout.contact_fragment) {

    val viewModel: ContactViewModel by viewModel { parametersOf(contact, this) }

    private val contact: UserContact by lazy {
        requireArguments().getSerializable(ARGUMENT_CONTACT) as UserContact
    }

    companion object {
        private const val ARGUMENT_CONTACT = "argument_contact"

        fun newInstance(contact: UserContact) = ContactFragment().apply {
            arguments = bundleOf(ARGUMENT_CONTACT to contact)
        }
    }

    override fun setupUI() {
        Glide.with(App.instance).load(viewModel.theme.previewFile).centerCrop().into(binding.preview)
        if (contact.photoThumbnailUri !== null)
            Glide.with(App.instance).load(contact.photoThumbnailUri).into(binding.avatar)
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        viewModel.callNumber.observe(this) { number ->
            viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
                if (it) activityAs<BaseActivity<*, *>>().call(number)
            }
        }
    }

    override fun provideViewModel() = viewModel

}