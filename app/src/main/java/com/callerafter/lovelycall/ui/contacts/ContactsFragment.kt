package com.callerafter.lovelycall.ui.contacts

import android.annotation.SuppressLint
import android.view.View
import androidx.core.os.bundleOf
import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.base.BaseFragment
import com.callerafter.lovelycall.databinding.ContactsFragmentBinding
import com.callerafter.lovelycall.ui.contact.ContactFragment
import com.callerafter.lovelycall.ui.contacts.ContactsFragment.Mode.DEFAULT
import com.callerafter.lovelycall.ui.contacts.ContactsFragment.Mode.INTERLOCUTOR_SELECTOR
import com.callerafter.lovelycall.ui.contacts.number.NumberDialog
import com.callerafter.lovelycall.ui.main.MainActivity
import com.callerafter.lovelycall.ui.theme.ThemeFragment
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
        with(binding.keyboard.binding.buttonCall) { post { visibility = View.VISIBLE } }
        binding.topPanel.setOnTouchListener { _, _ -> true }
        viewModel.onBackClickEvents.observe(this) { requireActivity().onBackPressed() }
        binding.keyboard.binding.buttonClose.setOnClickListener { binding.keyboard.visibility = View.GONE }
        binding.buttonKeyboard.setOnClickListener { binding.keyboard.visibility = View.VISIBLE }
        viewModel.selectedContacts.observe(this) {
            with(activityAs<MainActivity>()) {
                fragment(ThemeFragment::class.java)?.viewModel?.applyToContacts(it)
                onBackPressed()
            }
        }
        binding.keyboard.binding.buttonCall.setOnClickListener {
            if (mode == DEFAULT)
                TODO()
            else if (mode == INTERLOCUTOR_SELECTOR)
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
        TODO()
    }

    override fun provideViewModel() = viewModel

    enum class Mode {
        DEFAULT, CONTACT_SELECTOR, INTERLOCUTOR_SELECTOR
    }

}