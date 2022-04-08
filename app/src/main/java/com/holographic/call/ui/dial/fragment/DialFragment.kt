package com.holographic.call.ui.dial.fragment

import androidx.lifecycle.lifecycleScope
import com.holographic.call.R
import com.holographic.call.base.BaseActivity
import com.holographic.call.base.BaseFragment
import com.holographic.call.databinding.DialFragmentBinding
import com.holographic.call.ui.call.CallActivity
import com.holographic.call.ui.dial.activity.DialActivity
import com.holographic.call.ui.main.MainActivity
import com.holographic.call.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DialFragment : BaseFragment<DialViewModel, DialFragmentBinding>(R.layout.dial_fragment) {

    val viewModel: DialViewModel by viewModel { parametersOf(this) }

    var onButtonClick: (String) -> Unit = {}

    override fun setupUI() {
        viewModel.onButtonClickAdditional = onButtonClick
        binding.isBackVisible = requireActivity() !is DialActivity
        (requireActivity() as? DialActivity)?.intent?.data?.schemeSpecificPart?.let(viewModel.text::set)
        binding.buttonCall.setOnClickListener {
            viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
                if (it) {
                    activityAs<BaseActivity<*, * >>().call(viewModel.text.get()!!)
                    (requireActivity() as? CallActivity)?.removeNoneCallFragment(this)
                }
            }
        }
        binding.buttonBack.setOnClickListener(::onBackPressed)
    }

    private fun onBackPressed() =
        (requireActivity() as? MainActivity)?.onBackPressed() ?:
        (requireActivity() as? CallActivity)?.removeNoneCallFragment(this)

    override fun provideViewModel() = viewModel

}