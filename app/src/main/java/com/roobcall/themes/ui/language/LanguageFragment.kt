package com.roobcall.themes.ui.language

import com.roobcall.themes.R
import com.roobcall.themes.base.BaseFragment
import com.roobcall.themes.databinding.LanguageFragmentBinding
import com.roobcall.themes.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class LanguageFragment : BaseFragment<LanguageViewModel, LanguageFragmentBinding>(R.layout.language_fragment) {

    val viewModel: LanguageViewModel by viewModel()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)

    }

    override fun provideViewModel() = viewModel

}