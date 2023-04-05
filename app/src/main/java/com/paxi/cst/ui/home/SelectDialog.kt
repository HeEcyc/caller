package com.paxi.cst.ui.home

import com.paxi.cst.R
import com.paxi.cst.base.BaseActivity
import com.paxi.cst.base.BaseDialog
import com.paxi.cst.databinding.SelectDialogBinding
import com.paxi.cst.model.theme.Theme
import com.paxi.cst.ui.contacts.ContactsActivity
import com.paxi.cst.utils.setOnClickListener

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