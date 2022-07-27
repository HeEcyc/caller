package com.yellowmood.caller.ui.language

import com.yellowmood.caller.R
import com.yellowmood.caller.base.BaseFragment
import com.yellowmood.caller.databinding.LanguageFragmentBinding
import com.yellowmood.caller.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class LanguageFragment : BaseFragment<LanguageViewModel, LanguageFragmentBinding>(R.layout.language_fragment) {

    val viewModel: LanguageViewModel by viewModel()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)

    }

    override fun provideViewModel() = viewModel

}