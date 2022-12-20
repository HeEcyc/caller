package com.pecall.poscreen.ui.home

import com.pecall.poscreen.R
import com.pecall.poscreen.base.BaseActivity
import com.pecall.poscreen.base.BaseDialog
import com.pecall.poscreen.databinding.SelectDialogBinding
import com.pecall.poscreen.model.theme.Theme
import com.pecall.poscreen.ui.contacts.ContactsActivity
import com.pecall.poscreen.utils.setOnClickListener

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