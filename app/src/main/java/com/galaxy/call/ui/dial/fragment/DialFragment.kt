package com.galaxy.call.ui.dial.fragment

import androidx.lifecycle.lifecycleScope
import com.galaxy.call.R
import com.galaxy.call.base.BaseActivity
import com.galaxy.call.base.BaseFragment
import com.galaxy.call.databinding.DialFragmentBinding
import com.galaxy.call.ui.call.activity.CallActivity
import com.galaxy.call.ui.dial.activity.DialActivity
import com.galaxy.call.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DialFragment : BaseFragment<DialViewModel, DialFragmentBinding>(R.layout.dial_fragment) {

    val viewModel: DialViewModel by viewModel { parametersOf(this) }

    var onButtonClick: (String) -> Unit = {}

    override fun setupUI() {
        viewModel.onButtonClickAdditional = onButtonClick
        (requireActivity() as? DialActivity)?.intent?.data?.schemeSpecificPart?.let(viewModel.text::set)
        binding.buttonCall.setOnClickListener {
            viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
                if (it) {
                    activityAs<BaseActivity<*, *>>().call(viewModel.text.get()!!)
                    (requireActivity() as? CallActivity)?.removeNoneCallFragment(this)
                }
            }
        }
        binding.buttonBack.setOnClickListener(::onBackPressed)
    }

    private fun onBackPressed() {
        (requireActivity() as? CallActivity)?.removeNoneCallFragment(this) ?: requireActivity().onBackPressed()
    }

    override fun provideViewModel() = viewModel

}