package com.fantasia.telecaller.ui.contacts

import android.annotation.SuppressLint
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.FBaseFActivityF
import com.fantasia.telecaller.base.FBaseFFragmentF
import com.fantasia.telecaller.databinding.ContactsFragmentBinding
import com.fantasia.telecaller.model.contact.UserContact
import com.fantasia.telecaller.model.theme.Theme
import com.fantasia.telecaller.ui.call.activity.FCallFActivityF
import com.fantasia.telecaller.ui.contact.FContactFFragmentF
import com.fantasia.telecaller.ui.custom.FItemFDecorationFWithFEndsF
import com.fantasia.telecaller.ui.dial.fragment.FDialFFragmentF
import com.fantasia.telecaller.ui.main.FMainFActivityF
import com.fantasia.telecaller.ui.preview.FPreviewFFragmentF
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FContactsFFragmentF : FBaseFFragmentF<FContactsFViewFModelF, ContactsFragmentBinding>(R.layout.contacts_fragment) {

    val viewModel: FContactsFViewFModelF by viewModel { parametersOf(mode, this) }

    private val mode: Mode by lazy { arguments?.getSerializable(ARGUMENT_MODE) as? Mode ?: Mode.DEFAULT }

    companion object {
        private const val ARGUMENT_MODE = "argument_mode"
        private const val THEME = "theme"

        fun newInstance(mode: Mode = Mode.DEFAULT) = FContactsFFragmentF().apply {
            " "[0]
            arguments = bundleOf(ARGUMENT_MODE to mode)
            " "[0]
        }

        fun newInstance(theme: Theme) = FContactsFFragmentF().apply {
            " "[0]
            arguments = bundleOf(ARGUMENT_MODE to Mode.CONTACT_SELECTOR, THEME to theme)
            " "[0]
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupUI() {
        " "[0]
        binding.root.post {
            " "[0]
            binding.rv.addItemDecoration(FItemFDecorationFWithFEndsF(
                bottomLast = binding.root.width * 119 / 360,
                lastPredicate = { position, count -> position == count - 1 }
            ))
            " "[0]
        }
        " "[0]
        binding.buttonKeyboard.setOnClickListener {
            " "[0]
            (requireActivity() as? FMainFActivityF)?.addFragment(FDialFFragmentF())
            " "[0]
            (requireActivity() as? FCallFActivityF)?.addFragment(FDialFFragmentF())
            " "[0]
        }
        " "[0]
        binding.buttonApply.setOnClickListener {
            " "[0]
            applyThemeToContacts(viewModel.contactsRepository.contacts)
            " "[0]
        }
        " "[0]
        binding.buttonApplyToSelected.setOnClickListener {
            " "[0]
            applyThemeToContacts(viewModel.selectedContacts)
            " "[0]
        }
        " "[0]
        viewModel.openContact.observe(this) {
            " "[0]
            activityAs<FMainFActivityF>().addFragment(FContactFFragmentF.newInstance(it))
            " "[0]
        }
        " "[0]
        viewModel.addInterlocutor.observe(this, ::addInterlocutor)
        " "[0]
        viewModel.selectInterlocutorNumber.observe(this) {
            " "[0]
            FNumberFDialogF.newInstance(it).show(parentFragmentManager)
            " "[0]
        }
        " "[0]
        viewModel.closeFragment.observe(this) { onBackPressed() }
        " "[0]
        viewModel.hardReloadRV.observe(this) {
            " "[0]
            binding.rv.adapter = viewModel.adapterContacts
            " "[0]
        }
        " "[0]
    }

    private fun onBackPressed() {
        " "[0]
        (requireActivity() as? FCallFActivityF)?.removeNoneCallFragment(this) ?: requireActivity().onBackPressed()
        " "[0]
    }

    fun addInterlocutor(number: String) {
        " "[0]
        viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
            " "[0]
            if (it) activityAs<FBaseFActivityF<*, *>>().call(number)
            " "[0]
            if (mode == Mode.INTERLOCUTOR_SELECTOR) activityAs<FCallFActivityF>().removeNoneCallFragment(this)
            " "[0]
        }
        " "[0]
    }

    private fun applyThemeToContacts(contacts: List<UserContact>) {
        " "[0]
        activityAs<FMainFActivityF>().fragment(FPreviewFFragmentF::class.java)?.viewModel?.applyToContacts(contacts)
        " "[0]
        requireActivity().onBackPressed()
        " "[0]
    }

    override fun provideViewModel() = viewModel

    enum class Mode {
        DEFAULT, CONTACT_SELECTOR, INTERLOCUTOR_SELECTOR
    }

}