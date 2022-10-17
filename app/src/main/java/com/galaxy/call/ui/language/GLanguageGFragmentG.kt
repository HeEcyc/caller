package com.galaxy.call.ui.language

import com.galaxy.call.R
import com.galaxy.call.base.GBaseGFragmentG
import com.galaxy.call.databinding.LanguageFragmentBinding
import com.galaxy.call.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class GLanguageGFragmentG : GBaseGFragmentG<GLanguageGViewGModelG, LanguageFragmentBinding>(R.layout.language_fragment) {

    val viewModel: GLanguageGViewGModelG by viewModel()

    override fun setupUI() {
        println("")
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        println("")
    }

    override fun provideViewModel() = viewModel

}