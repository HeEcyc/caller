package com.delice.cscreen.ui.home

import com.delice.cscreen.R
import com.delice.cscreen.base.BaseActivity
import com.delice.cscreen.base.BaseDialog
import com.delice.cscreen.databinding.SelectDialogBinding
import com.delice.cscreen.model.theme.Theme
import com.delice.cscreen.ui.contacts.ContactsActivity
import com.delice.cscreen.utils.setOnClickListener

class SelectDialog : BaseDialog<SelectDialogBinding>(R.layout.select_dialog) {

    var theme: Theme? = null

    override fun setupUI() {
        val theme = theme
        if (theme === null) { dismiss(); return }
        binding.buttonClose.setOnClickListener(::dismiss)
        binding.buttonAll.setOnClickListener {
            activityAs<BaseActivity<*,*>>().fragment(HomeFragment::class.java)?.viewModel?.applyToAll(theme)
            AppliedDialog().show(parentFragmentManager)
            dismiss()
        }
        binding.buttonSelected.setOnClickListener {
            activityAs<BaseActivity<*,*>>().fragment(HomeFragment::class.java)?.contactsLauncher?.launch(
                (ContactsActivity.newIntent(requireContext()))
            )
            dismiss()
        }
    }

}