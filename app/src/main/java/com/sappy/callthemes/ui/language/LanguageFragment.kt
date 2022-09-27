package com.sappy.callthemes.ui.language

import com.sappy.callthemes.R
import com.sappy.callthemes.base.BaseFragment
import com.sappy.callthemes.databinding.LanguageFragmentBinding
import com.sappy.callthemes.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class LanguageFragment : BaseFragment<LanguageViewModel, LanguageFragmentBinding>(R.layout.language_fragment) {

    val viewModel: LanguageViewModel by viewModel()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)

    }

    override fun provideViewModel() = viewModel

}