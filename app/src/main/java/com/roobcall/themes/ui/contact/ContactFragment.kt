package com.roobcall.themes.ui.contact

import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.roobcall.themes.App
import com.roobcall.themes.R
import com.roobcall.themes.base.BaseActivity
import com.roobcall.themes.base.BaseFragment
import com.roobcall.themes.databinding.ContactFragmentBinding
import com.roobcall.themes.model.contact.UserContact
import com.roobcall.themes.utils.setOnClickListener
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