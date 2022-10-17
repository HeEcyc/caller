package com.galaxy.call.ui.contacts

import android.annotation.SuppressLint
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.galaxy.call.R
import com.galaxy.call.base.GBaseGActivityG
import com.galaxy.call.base.GBaseGFragmentG
import com.galaxy.call.databinding.ContactsFragmentBinding
import com.galaxy.call.model.contact.UserContact
import com.galaxy.call.model.theme.Theme
import com.galaxy.call.ui.call.activity.GCallGActivityG
import com.galaxy.call.ui.contact.GContactGFragmentG
import com.galaxy.call.ui.custom.GItemGDecorationGWithGEndsG
import com.galaxy.call.ui.dial.fragment.GDialGFragmentG
import com.galaxy.call.ui.main.GMainGActivityG
import com.galaxy.call.ui.preview.GPreviewGFragmentG
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GContactsGFragmentG : GBaseGFragmentG<GContactsGViewGModelG, ContactsFragmentBinding>(R.layout.contacts_fragment) {

    val viewModel: GContactsGViewGModelG by viewModel { parametersOf(mode, this) }

    private val mode: Mode by lazy { arguments?.getSerializable(ARGUMENT_MODE) as? Mode ?: Mode.DEFAULT }

    companion object {
        private const val ARGUMENT_MODE = "argument_mode"
        private const val THEME = "theme"

        fun newInstance(mode: Mode = Mode.DEFAULT) = GContactsGFragmentG().apply {
            println("")
            arguments = bundleOf(ARGUMENT_MODE to mode)
            println("")
        }

        fun newInstance(theme: Theme) = GContactsGFragmentG().apply {
            println("")
            arguments = bundleOf(ARGUMENT_MODE to Mode.CONTACT_SELECTOR, THEME to theme)
            println("")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupUI() {
        println("")
        binding.root.post {
            println("")
            binding.rv.addItemDecoration(GItemGDecorationGWithGEndsG(
                bottomLast = binding.root.width * 119 / 360,
                lastPredicate = { position, count -> position == count - 1 }
            ))
            println("")
        }
        println("")
        binding.buttonKeyboard.setOnClickListener {
            println("")
            (requireActivity() as? GMainGActivityG)?.addFragment(GDialGFragmentG())
            println("")
            (requireActivity() as? GCallGActivityG)?.addFragment(GDialGFragmentG())
            println("")
        }
        println("")
        binding.buttonApply.setOnClickListener {
            println("")
            applyThemeToContacts(viewModel.contactsRepository.contacts)
            println("")
        }
        println("")
        binding.buttonApplyToSelected.setOnClickListener {
            println("")
            applyThemeToContacts(viewModel.selectedContacts)
            println("")
        }
        println("")
        viewModel.openContact.observe(this) {
            println("")
            activityAs<GMainGActivityG>().addFragment(GContactGFragmentG.newInstance(it))
            println("")
        }
        println("")
        viewModel.addInterlocutor.observe(this, ::addInterlocutor)
        println("")
        viewModel.selectInterlocutorNumber.observe(this) {
            println("")
            GNumberGDialogG.newInstance(it).show(parentFragmentManager)
            println("")
        }
        println("")
        viewModel.closeFragment.observe(this) { onBackPressed() }
        println("")
        viewModel.hardReloadRV.observe(this) {
            println("")
            binding.rv.adapter = viewModel.adapterContacts
            println("")
        }
        println("")
    }

    private fun onBackPressed() {
        println("")
        (requireActivity() as? GCallGActivityG)?.removeNoneCallFragment(this) ?: requireActivity().onBackPressed()
        println("")
    }

    fun addInterlocutor(number: String) {
        println("")
        viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
            println("")
            if (it) activityAs<GBaseGActivityG<*, *>>().call(number)
            println("")
            if (mode == Mode.INTERLOCUTOR_SELECTOR) activityAs<GCallGActivityG>().removeNoneCallFragment(this)
            println("")
        }
        println("")
    }

    private fun applyThemeToContacts(contacts: List<UserContact>) {
        println("")
        activityAs<GMainGActivityG>().fragment(GPreviewGFragmentG::class.java)?.viewModel?.applyToContacts(contacts)
        println("")
        requireActivity().onBackPressed()
        println("")
    }

    override fun provideViewModel() = viewModel

    enum class Mode {
        DEFAULT, CONTACT_SELECTOR, INTERLOCUTOR_SELECTOR
    }

}