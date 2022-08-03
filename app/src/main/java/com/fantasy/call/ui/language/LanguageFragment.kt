package com.fantasy.call.ui.language

import com.fantasy.call.R
import com.fantasy.call.base.BaseFragment
import com.fantasy.call.databinding.LanguageFragmentBinding
import com.fantasy.call.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class LanguageFragment : BaseFragment<LanguageViewModel, LanguageFragmentBinding>(R.layout.language_fragment) {

    val viewModel: LanguageViewModel by viewModel()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)

    }

    override fun provideViewModel() = viewModel

}