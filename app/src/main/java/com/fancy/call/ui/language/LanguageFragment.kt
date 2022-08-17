package com.fancy.call.ui.language

import com.fancy.call.R
import com.fancy.call.base.BaseFragment
import com.fancy.call.databinding.LanguageFragmentBinding
import com.fancy.call.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class LanguageFragment : BaseFragment<LanguageViewModel, LanguageFragmentBinding>(R.layout.language_fragment) {

    val viewModel: LanguageViewModel by viewModel()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)

    }

    override fun provideViewModel() = viewModel

}