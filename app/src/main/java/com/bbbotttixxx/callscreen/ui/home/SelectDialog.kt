package com.bbbotttixxx.callscreen.ui.home

import com.bbbotttixxx.callscreen.R
import com.bbbotttixxx.callscreen.base.BaseActivity
import com.bbbotttixxx.callscreen.base.BaseDialog
import com.bbbotttixxx.callscreen.databinding.SelectDialogBinding
import com.bbbotttixxx.callscreen.model.theme.Theme
import com.bbbotttixxx.callscreen.ui.contacts.ContactsActivity
import com.bbbotttixxx.callscreen.utils.setOnClickListener

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