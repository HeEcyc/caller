package com.glass.call.ui.language

import com.glass.call.R
import com.glass.call.base.BaseFragment
import com.glass.call.databinding.LanguageFragmentBinding
import com.glass.call.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class LanguageFragment : BaseFragment<LanguageViewModel, LanguageFragmentBinding>(R.layout.language_fragment) {

    val viewModel: LanguageViewModel by viewModel()

    override fun setupUI() {
        binding.topPanel.setOnClickListener {}
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)

    }

    override fun provideViewModel() = viewModel

}