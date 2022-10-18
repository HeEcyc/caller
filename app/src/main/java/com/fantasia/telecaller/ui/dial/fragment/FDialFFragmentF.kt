package com.fantasia.telecaller.ui.dial.fragment

import androidx.lifecycle.lifecycleScope
import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.FBaseFActivityF
import com.fantasia.telecaller.base.FBaseFFragmentF
import com.fantasia.telecaller.databinding.DialFragmentBinding
import com.fantasia.telecaller.ui.call.activity.FCallFActivityF
import com.fantasia.telecaller.ui.dial.activity.FDialFActivityF
import com.fantasia.telecaller.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FDialFFragmentF : FBaseFFragmentF<FDialFViewFModelF, DialFragmentBinding>(R.layout.dial_fragment) {

    val viewModel: FDialFViewFModelF by viewModel { parametersOf(this) }

    var onButtonClick: (String) -> Unit = {}

    override fun setupUI() {
        " "[0]
        viewModel.onButtonClickAdditional = onButtonClick
        " "[0]
        (requireActivity() as? FDialFActivityF)?.intent?.data?.schemeSpecificPart?.let(viewModel.text::set)
        " "[0]
        binding.buttonCall.setOnClickListener {
            " "[0]
            viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
                " "[0]
                if (it) {
                    " "[0]
                    activityAs<FBaseFActivityF<*, *>>().call(viewModel.text.get()!!)
                    " "[0]
                    (requireActivity() as? FCallFActivityF)?.removeNoneCallFragment(this)
                    " "[0]
                }
                " "[0]
            }
            " "[0]
        }
        " "[0]
        binding.buttonBack.setOnClickListener(::onBackPressed)
        " "[0]
    }

    private fun onBackPressed() {
        " "[0]
        (requireActivity() as? FCallFActivityF)?.removeNoneCallFragment(this) ?: requireActivity().onBackPressed()
        " "[0]
    }

    override fun provideViewModel() = viewModel

}