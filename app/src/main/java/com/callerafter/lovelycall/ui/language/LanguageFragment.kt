package com.callerafter.lovelycall.ui.language

import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.base.BaseFragment
import com.callerafter.lovelycall.databinding.LanguageFragmentBinding
import com.callerafter.lovelycall.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class LanguageFragment : BaseFragment<LanguageViewModel, LanguageFragmentBinding>(
    R.layout.language_fragment
) {

    private val viewModel: LanguageViewModel by viewModel()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
    }

    override fun provideViewModel() = viewModel

}