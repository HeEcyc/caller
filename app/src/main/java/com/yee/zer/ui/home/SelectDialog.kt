package com.yee.zer.ui.home

import com.yee.zer.R
import com.yee.zer.base.BaseActivity
import com.yee.zer.base.BaseDialog
import com.yee.zer.databinding.SelectDialogBinding
import com.yee.zer.model.theme.Theme
import com.yee.zer.ui.contacts.ContactsActivity
import com.yee.zer.utils.setOnClickListener

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