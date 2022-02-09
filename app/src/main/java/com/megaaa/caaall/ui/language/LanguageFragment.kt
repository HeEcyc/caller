package com.megaaa.caaall.ui.language

import com.megaaa.caaall.R
import com.megaaa.caaall.base.BaseFragment
import com.megaaa.caaall.databinding.LanguageFragmentBinding
import com.megaaa.caaall.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class LanguageFragment : BaseFragment<LanguageViewModel, LanguageFragmentBinding>(
    R.layout.language_fragment
) {

    private val viewModel: LanguageViewModel by viewModel()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
    }

    override fun provideViewModel() = viewModel

}