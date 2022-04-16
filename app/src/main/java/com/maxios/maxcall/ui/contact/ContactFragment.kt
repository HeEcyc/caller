package com.maxios.maxcall.ui.contact

import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.maxios.maxcall.App
import com.maxios.maxcall.R
import com.maxios.maxcall.base.BaseActivity
import com.maxios.maxcall.base.BaseFragment
import com.maxios.maxcall.databinding.ContactFragmentBinding
import com.maxios.maxcall.model.contact.UserContact
import com.maxios.maxcall.ui.main.MainActivity
import com.maxios.maxcall.ui.theme.ThemeFragment
import com.maxios.maxcall.utils.setOnClickListener
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
            activityAs<MainActivity>().addFragmentNoBackStack(ThemeFragment())
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
            addFragmentNoBackStack(newInstance)
        }
    }

    override fun provideViewModel() = viewModel

}