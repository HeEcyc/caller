package com.vefercal.ler.ui.home

import com.vefercal.ler.R
import com.vefercal.ler.base.BaseActivity
import com.vefercal.ler.base.BaseDialog
import com.vefercal.ler.databinding.SelectDialogBinding
import com.vefercal.ler.model.theme.Theme
import com.vefercal.ler.ui.contacts.ContactsActivity
import com.vefercal.ler.utils.setOnClickListener

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