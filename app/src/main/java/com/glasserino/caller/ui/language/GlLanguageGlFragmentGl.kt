package com.glasserino.caller.ui.language

import com.glasserino.caller.R
import com.glasserino.caller.base.GlBaseGlFragmentGl
import com.glasserino.caller.databinding.LanguageFragmentBinding
import com.glasserino.caller.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class GlLanguageGlFragmentGl : GlBaseGlFragmentGl<GlLanguageGlViewGlModelGl, LanguageFragmentBinding>(R.layout.language_fragment) {

    val viewModel: GlLanguageGlViewGlModelGl by viewModel()

    override fun setupUI() {
        listOf<Any>().isEmpty()
        binding.topPanel.setOnClickListener {}
        listOf<Any>().isEmpty()
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        listOf<Any>().isEmpty()
    }

    override fun provideViewModel() = viewModel

}