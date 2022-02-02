package com.callerafter.lovelycall.ui.theme

import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.base.BaseDialog
import com.callerafter.lovelycall.databinding.OptionsDialogBinding
import com.callerafter.lovelycall.ui.contacts.ContactsFragment
import com.callerafter.lovelycall.ui.main.MainActivity

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