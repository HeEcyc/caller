package com.galala.lalaxy.ui.language

import com.galala.lalaxy.R
import com.galala.lalaxy.base.GBaseGFragmentG
import com.galala.lalaxy.databinding.LanguageFragmentBinding
import com.galala.lalaxy.utils.setOnClickListener
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