package com.glasserino.caller.ui.contacts

import android.annotation.SuppressLint
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.glasserino.caller.R
import com.glasserino.caller.base.GlBaseGlActivityGl
import com.glasserino.caller.base.GlBaseGlFragmentGl
import com.glasserino.caller.databinding.ContactsFragmentBinding
import com.glasserino.caller.model.contact.UserContact
import com.glasserino.caller.model.theme.Theme
import com.glasserino.caller.ui.call.GlCallGlActivityGl
import com.glasserino.caller.ui.contact.GlContactGLFragmentGl
import com.glasserino.caller.ui.contacts.dialog.GlNumberGlDialogGl
import com.glasserino.caller.ui.dial.fragment.GlDialGlFragmentGl
import com.glasserino.caller.ui.home.GlHomeGlFragmentGl
import com.glasserino.caller.ui.main.GlMainGlActivityGl
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GlContactsGlFragmentGl : GlBaseGlFragmentGl<GlContactsGlViewGlModelGl, ContactsFragmentBinding>(R.layout.contacts_fragment) {

    val viewModel: GlContactsGlViewGlModelGl by viewModel { parametersOf(mode, this) }

    private val mode: Mode by lazy { arguments?.getSerializable(ARGUMENT_MODE) as? Mode ?: Mode.DEFAULT }
    private val theme: Theme? by lazy { arguments?.getSerializable(THEME) as? Theme }

    companion object {
        private const val ARGUMENT_MODE = "argument_mode"
        private const val THEME = "theme"

        fun newInstance(mode: Mode = Mode.DEFAULT) = GlContactsGlFragmentGl().apply {
            listOf<Any>().isEmpty()
            arguments = bundleOf(ARGUMENT_MODE to mode)
            listOf<Any>().isEmpty()
        }

        fun newInstance(theme: Theme) = GlContactsGlFragmentGl().apply {
            listOf<Any>().isEmpty()
            arguments = bundleOf(ARGUMENT_MODE to Mode.CONTACT_SELECTOR, THEME to theme)
            listOf<Any>().isEmpty()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupUI() {
        listOf<Any>().isEmpty()
        binding.topPanel.setOnClickListener {}
        listOf<Any>().isEmpty()
        binding.bottomPanel.setOnClickListener {}
        listOf<Any>().isEmpty()
        binding.buttonKeyboard.setOnClickListener {
            listOf<Any>().isEmpty()
            (requireActivity() as? GlMainGlActivityGl)?.addFragment(GlDialGlFragmentGl())
            listOf<Any>().isEmpty()
            (requireActivity() as? GlCallGlActivityGl)?.addFragment(GlDialGlFragmentGl())
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        binding.buttonApply.setOnClickListener {
            listOf<Any>().isEmpty()
            applyThemeToContacts(viewModel.contactsRepository.contacts)
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        binding.buttonApplyToSelected.setOnClickListener {
            listOf<Any>().isEmpty()
            applyThemeToContacts(viewModel.selectedContacts)
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        viewModel.openContact.observe(this) {
            listOf<Any>().isEmpty()
            activityAs<GlMainGlActivityGl>().addFragment(GlContactGLFragmentGl.newInstance(it))
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        viewModel.addInterlocutor.observe(this, ::addInterlocutor)
        listOf<Any>().isEmpty()
        viewModel.selectInterlocutorNumber.observe(this) {
            listOf<Any>().isEmpty()
            GlNumberGlDialogGl.newInstance(it).show(parentFragmentManager)
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        viewModel.closeFragment.observe(this) { onBackPressed() }
        listOf<Any>().isEmpty()
        viewModel.hardReloadRV.observe(this) {
            listOf<Any>().isEmpty()
            binding.rv.adapter = viewModel.adapterContacts
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

    private fun onBackPressed() {
        listOf<Any>().isEmpty()
        (requireActivity() as? GlCallGlActivityGl)?.removeNoneCallFragment(this) ?: requireActivity().onBackPressed()
        listOf<Any>().isEmpty()
    }

    fun addInterlocutor(number: String) {
        listOf<Any>().isEmpty()
        viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
            listOf<Any>().isEmpty()
            if (it) activityAs<GlBaseGlActivityGl<*, *>>().call(number)
            listOf<Any>().isEmpty()
            if (mode == Mode.INTERLOCUTOR_SELECTOR) activityAs<GlCallGlActivityGl>().removeNoneCallFragment(this)
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

    private fun applyThemeToContacts(contacts: List<UserContact>) {
        listOf<Any>().isEmpty()
        activityAs<GlMainGlActivityGl>().fragment(GlHomeGlFragmentGl::class.java)?.viewModel?.applyThemeToContacts(theme!!, contacts)
        listOf<Any>().isEmpty()
        requireActivity().onBackPressed()
        listOf<Any>().isEmpty()
    }

    override fun provideViewModel() = viewModel

    enum class Mode {
        DEFAULT, CONTACT_SELECTOR, INTERLOCUTOR_SELECTOR
    }

}