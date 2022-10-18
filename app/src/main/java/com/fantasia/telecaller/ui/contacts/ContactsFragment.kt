package com.fantasia.telecaller.ui.contacts

import android.annotation.SuppressLint
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.BaseActivity
import com.fantasia.telecaller.base.BaseFragment
import com.fantasia.telecaller.databinding.ContactsFragmentBinding
import com.fantasia.telecaller.model.contact.UserContact
import com.fantasia.telecaller.model.theme.Theme
import com.fantasia.telecaller.ui.call.activity.CallActivity
import com.fantasia.telecaller.ui.contact.ContactFragment
import com.fantasia.telecaller.ui.custom.ItemDecorationWithEnds
import com.fantasia.telecaller.ui.dial.fragment.DialFragment
import com.fantasia.telecaller.ui.main.MainActivity
import com.fantasia.telecaller.ui.preview.PreviewFragment
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
        binding.root.post {
            binding.rv.addItemDecoration(ItemDecorationWithEnds(
                bottomLast = binding.root.width * 119 / 360,
                lastPredicate = { position, count -> position == count - 1 }
            ))
        }
        binding.buttonKeyboard.setOnClickListener {
            (requireActivity() as? MainActivity)?.addFragment(DialFragment())
            (requireActivity() as? CallActivity)?.addFragment(DialFragment())
        }
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
            NumberDialog.newInstance(it).show(parentFragmentManager)
        }
        viewModel.closeFragment.observe(this) { onBackPressed() }
        viewModel.hardReloadRV.observe(this) {
            binding.rv.adapter = viewModel.adapterContacts
        }
    }

    private fun onBackPressed() {
        (requireActivity() as? CallActivity)?.removeNoneCallFragment(this) ?: requireActivity().onBackPressed()
    }

    fun addInterlocutor(number: String) {
        viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
            if (it) activityAs<BaseActivity<*, *>>().call(number)
            if (mode == Mode.INTERLOCUTOR_SELECTOR) activityAs<CallActivity>().removeNoneCallFragment(this)
        }
    }

    private fun applyThemeToContacts(contacts: List<UserContact>) {
        activityAs<MainActivity>().fragment(PreviewFragment::class.java)?.viewModel?.applyToContacts(contacts)
        requireActivity().onBackPressed()
    }

    override fun provideViewModel() = viewModel

    enum class Mode {
        DEFAULT, CONTACT_SELECTOR, INTERLOCUTOR_SELECTOR
    }

}