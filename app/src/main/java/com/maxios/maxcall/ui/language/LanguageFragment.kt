package com.maxios.maxcall.ui.language

import com.maxios.maxcall.R
import com.maxios.maxcall.base.BaseFragment
import com.maxios.maxcall.databinding.LanguageFragmentBinding
import com.maxios.maxcall.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class LanguageFragment : BaseFragment<LanguageViewModel, LanguageFragmentBinding>(R.layout.language_fragment) {

    val viewModel: LanguageViewModel by viewModel()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
    }

    override fun provideViewModel() = viewModel

}