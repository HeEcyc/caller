package com.threed.caller.ui.language

import com.threed.caller.R
import com.threed.caller.base.BaseFragment
import com.threed.caller.databinding.LanguageFragmentBinding
import com.threed.caller.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class LanguageFragment : BaseFragment<LanguageViewModel, LanguageFragmentBinding>(R.layout.language_fragment) {

    val viewModel: LanguageViewModel by viewModel()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)

    }

    override fun provideViewModel() = viewModel

}