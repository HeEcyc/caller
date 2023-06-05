package com.fusiecal.ler.ui.home

import com.fusiecal.ler.R
import com.fusiecal.ler.base.BaseActivity
import com.fusiecal.ler.base.BaseDialog
import com.fusiecal.ler.databinding.SelectDialogBinding
import com.fusiecal.ler.model.theme.Theme
import com.fusiecal.ler.ui.contacts.ContactsActivity
import com.fusiecal.ler.utils.setOnClickListener

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