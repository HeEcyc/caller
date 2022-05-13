package com.glass.call.ui.contacts

import android.annotation.SuppressLint
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.glass.call.R
import com.glass.call.base.BaseActivity
import com.glass.call.base.BaseFragment
import com.glass.call.databinding.ContactsFragmentBinding
import com.glass.call.model.contact.UserContact
import com.glass.call.model.theme.Theme
import com.glass.call.ui.contact.ContactFragment
import com.glass.call.ui.home.HomeFragment
import com.glass.call.ui.main.MainActivity
import com.glass.call.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ContactsFragment : BaseFragment<ContactsViewModel, ContactsFragmentBinding>(R.layout.contacts_fragment) {

    val viewModel: ContactsViewModel by viewModel { parametersOf(mode, this) }

    private val mode: Mode by lazy { arguments?.getSerializable(ARGUMENT_MODE) as? Mode ?: Mode.DEFAULT }
    private val theme: Theme? by lazy { arguments?.getSerializable(THEME) as? Theme }

    companion object {
        private const val ARGUMENT_MODE = "argument_mode"
        private const val THEME = "theme"

        fun newInstance(mode: Mode = Mode.DEFAULT) = ContactsFragment().apply {
            arguments = bundleOf(ARGUMENT_MODE to mode)
        }

        fun newInstance(theme: Theme) = ContactsFragment().apply {
            arguments = bundleOf(ARGUMENT_MODE to Mode.CONTACT_SELECTOR, THEME to theme)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupUI() {
        binding.topPanel.setOnClickListener {}
        binding.bottomPanel.setOnClickListener {}
//        binding.buttonKeyboard.setOnClickListener {
//            (requireActivity() as? MainActivity)?.addFragment(DialFragment())
//            (requireActivity() as? CallActivity)?.addFragment(DialFragment())
//        }todo
        binding.buttonApply.setOnClickListener {
            applyThemeToContacts(viewModel.contactsRepository.contacts)
        }
        binding.buttonApplyToSelected.setOnClickListener {
            applyThemeToContacts(viewModel.selectedContacts)
        }
        viewModel.openContact.observe(this) {
            activityAs<MainActivity>().addFragment(ContactFragment.newInstance(it))
        }
        viewModel.addInterlocutor.observe(this, ::addInterlocutor)
        viewModel.selectInterlocutorNumber.observe(this) {
//            NumberDialog.newInstance(it).show(parentFragmentManager)todo
        }
        viewModel.closeFragment.observe(this) { onBackPressed() }
        viewModel.hardReloadRV.observe(this) {
            binding.rv.adapter = viewModel.adapterContacts
        }
    }

    private fun onBackPressed() {
//        (requireActivity() as? CallActivity)?.removeNoneCallFragment(this) ?:todo
        requireActivity().onBackPressed()
    }

    fun addInterlocutor(number: String) {
//        viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
//            if (it) activityAs<BaseActivity<*, *>>().call(number)
//            if (mode == Mode.INTERLOCUTOR_SELECTOR) activityAs<CallActivity>().removeNoneCallFragment(this)
//        }todo
    }

    private fun applyThemeToContacts(contacts: List<UserContact>) {
        activityAs<MainActivity>().fragment(HomeFragment::class.java)?.viewModel?.applyThemeToContacts(theme!!, contacts)
        requireActivity().onBackPressed()
    }

    override fun provideViewModel() = viewModel

    enum class Mode {
        DEFAULT, CONTACT_SELECTOR, INTERLOCUTOR_SELECTOR
    }

}