package com.fantasia.telecaller.ui.language

import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.BaseFragment
import com.fantasia.telecaller.databinding.LanguageFragmentBinding
import com.fantasia.telecaller.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class LanguageFragment : BaseFragment<LanguageViewModel, LanguageFragmentBinding>(R.layout.language_fragment) {

    val viewModel: LanguageViewModel by viewModel()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)

    }

    override fun provideViewModel() = viewModel

}