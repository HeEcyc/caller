package com.holographic.call.ui.contact

import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.holographic.call.App
import com.holographic.call.R
import com.holographic.call.base.BaseActivity
import com.holographic.call.base.BaseFragment
import com.holographic.call.databinding.ContactFragmentBinding
import com.holographic.call.model.contact.UserContact
import com.holographic.call.ui.main.MainActivity
import com.holographic.call.ui.theme.ThemeFragment
import com.holographic.call.utils.setOnClickListener
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
        Glide.with(App.instance).load(viewModel.theme.previewFile).centerCrop().into(binding.themePreview)
        if (contact.photoThumbnailUri !== null)
            Glide.with(App.instance).load(contact.photoThumbnailUri).into(binding.contactPicture)
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        binding.buttonEdit.setOnClickListener {
            activityAs<MainActivity>().addFragment(ThemeFragment())
        }
        viewModel.callNumber.observe(this) { number ->
            viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
                if (it) activityAs<BaseActivity<*, *>>().call(number)
            }
        }
        viewModel.recreateFragment.observe(this) { recreate() }
    }

    private fun recreate() {
        val newInstance = ContactFragment()
        newInstance.arguments = arguments
        with(activityAs<MainActivity>()) {
            removeFragment(this@ContactFragment)
            addFragment(newInstance)
        }
    }

    override fun provideViewModel() = viewModel

}