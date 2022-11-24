package com.vefercal.ler.ui.language

import com.vefercal.ler.R
import com.vefercal.ler.base.BaseFragment
import com.vefercal.ler.databinding.LanguageFragmentBinding
import com.vefercal.ler.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class LanguageFragment : BaseFragment<LanguageViewModel, LanguageFragmentBinding>(R.layout.language_fragment) {

    val viewModel: LanguageViewModel by viewModel()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)

    }

    override fun provideViewModel() = viewModel

}