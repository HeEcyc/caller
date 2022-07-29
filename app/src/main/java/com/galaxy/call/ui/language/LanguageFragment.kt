package com.galaxy.call.ui.language

import com.galaxy.call.R
import com.galaxy.call.base.BaseFragment
import com.galaxy.call.databinding.LanguageFragmentBinding
import com.galaxy.call.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class LanguageFragment : BaseFragment<LanguageViewModel, LanguageFragmentBinding>(R.layout.language_fragment) {

    val viewModel: LanguageViewModel by viewModel()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)

    }

    override fun provideViewModel() = viewModel

}