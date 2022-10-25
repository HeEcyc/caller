package com.stacky.caller.ui.language

import com.stacky.caller.R
import com.stacky.caller.base.BaseFragment
import com.stacky.caller.databinding.LanguageFragmentBinding
import com.stacky.caller.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class LanguageFragment : BaseFragment<LanguageViewModel, LanguageFragmentBinding>(R.layout.language_fragment) {

    val viewModel: LanguageViewModel by viewModel()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)

    }

    override fun provideViewModel() = viewModel

}