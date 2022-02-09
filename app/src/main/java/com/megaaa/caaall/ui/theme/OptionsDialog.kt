package com.megaaa.caaall.ui.theme

import com.megaaa.caaall.R
import com.megaaa.caaall.base.BaseDialog
import com.megaaa.caaall.databinding.OptionsDialogBinding
import com.megaaa.caaall.ui.contacts.ContactsFragment
import com.megaaa.caaall.ui.main.MainActivity

class OptionsDialog : BaseDialog<OptionsDialogBinding>(R.layout.options_dialog) {

    override fun setupUI() {
        binding.buttonYes.setOnClickListener {
            activityAs<MainActivity>().fragment(ThemeFragment::class.java)?.viewModel?.applyToAll()
            dismiss()
        }
        binding.buttonNo.setOnClickListener {
            activityAs<MainActivity>().addFragment(ContactsFragment.newInstance(ContactsFragment.Mode.CONTACT_SELECTOR))
            dismiss()
        }
    }

}