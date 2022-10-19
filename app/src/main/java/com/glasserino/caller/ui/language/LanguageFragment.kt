package com.glasserino.caller.ui.language

import com.glasserino.caller.R
import com.glasserino.caller.base.BaseFragment
import com.glasserino.caller.databinding.LanguageFragmentBinding
import com.glasserino.caller.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class LanguageFragment : BaseFragment<LanguageViewModel, LanguageFragmentBinding>(R.layout.language_fragment) {

    val viewModel: LanguageViewModel by viewModel()

    override fun setupUI() {
        binding.topPanel.setOnClickListener {}
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)

    }

    override fun provideViewModel() = viewModel

}