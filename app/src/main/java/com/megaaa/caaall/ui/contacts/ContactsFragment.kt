package com.megaaa.caaall.ui.contacts

import android.annotation.SuppressLint
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.megaaa.caaall.R
import com.megaaa.caaall.base.BaseActivity
import com.megaaa.caaall.base.BaseFragment
import com.megaaa.caaall.databinding.ContactsFragmentBinding
import com.megaaa.caaall.repository.PermissionRepository
import com.megaaa.caaall.ui.call.CallActivity
import com.megaaa.caaall.ui.contact.ContactFragment
import com.megaaa.caaall.ui.contacts.ContactsFragment.Mode.*
import com.megaaa.caaall.ui.contacts.number.NumberDialog
import com.megaaa.caaall.ui.main.MainActivity
import com.megaaa.caaall.ui.theme.ThemeFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ContactsFragment : BaseFragment<ContactsViewModel, ContactsFragmentBinding>(R.layout.contacts_fragment) {

    private val viewModel: ContactsViewModel by viewModel { parametersOf(mode) }
    private val mode: Mode by lazy { arguments?.getSerializable(ARGUMENT_MODE) as? Mode ?: DEFAULT }

    companion object {
        private const val ARGUMENT_MODE = "argument_mode"

        fun newInstance(mode: Mode = DEFAULT) = ContactsFragment().apply {
            arguments = bundleOf(ARGUMENT_MODE to mode)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupUI() {
        viewModel.permissionRepository = PermissionRepository(this)
        with(binding.keyboard.binding.buttonCall) { post { visibility = View.VISIBLE } }
        binding.topPanel.setOnTouchListener { _, _ -> true }
        binding.keyboard.setOnClickListener {}
        viewModel.onBackClickEvents.observe(this) {
            if (mode == INTERLOCUTOR_SELECTOR)
                activityAs<CallActivity>().removeNoneCallFragment(this)
            else
                requireActivity().onBackPressed() }
        binding.keyboard.binding.buttonClose.setOnClickListener { binding.keyboard.visibility = View.GONE }
        binding.buttonKeyboard.setOnClickListener { binding.keyboard.visibility = View.VISIBLE }
        viewModel.selectedContacts.observe(this) {
            with(activityAs<MainActivity>()) {
                fragment(ThemeFragment::class.java)?.viewModel?.applyToContacts(it)
                onBackPressed()
            }
        }
        binding.keyboard.binding.buttonCall.setOnClickListener {
            addInterlocutor(binding.keyboard.binding.textView.text.toString())
        }
        viewModel.addInterlocutor.observe(this, ::addInterlocutor)
        viewModel.selectInterlocutorNumber.observe(this) {
            NumberDialog.newInstance(it).show(parentFragmentManager)
        }
        viewModel.openContact.observe(this) {
            activityAs<MainActivity>().addFragment(ContactFragment.newInstance(it))
        }
    }

    fun addInterlocutor(number: String) {
        viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
            if (it) activityAs<BaseActivity<*, *>>().call(number)
            if (mode == INTERLOCUTOR_SELECTOR) activityAs<CallActivity>().removeNoneCallFragment(this)
        }
    }

    override fun provideViewModel() = viewModel

    enum class Mode {
        DEFAULT, CONTACT_SELECTOR, INTERLOCUTOR_SELECTOR
    }

}