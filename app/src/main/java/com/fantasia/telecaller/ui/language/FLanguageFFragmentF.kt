package com.fantasia.telecaller.ui.language

import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.FBaseFFragmentF
import com.fantasia.telecaller.databinding.LanguageFragmentBinding
import com.fantasia.telecaller.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class FLanguageFFragmentF : FBaseFFragmentF<FLanguageFViewFModelF, LanguageFragmentBinding>(R.layout.language_fragment) {

    val viewModel: FLanguageFViewFModelF by viewModel()

    override fun setupUI() {
        " "[0]
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        " "[0]
    }

    override fun provideViewModel() = viewModel

}