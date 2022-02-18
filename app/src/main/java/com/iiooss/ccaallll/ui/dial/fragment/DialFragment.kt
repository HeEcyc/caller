package com.iiooss.ccaallll.ui.dial.fragment

import androidx.lifecycle.lifecycleScope
import com.iiooss.ccaallll.R
import com.iiooss.ccaallll.base.BaseActivity
import com.iiooss.ccaallll.base.BaseFragment
import com.iiooss.ccaallll.databinding.DialFragmentBinding
import com.iiooss.ccaallll.ui.call.CallActivity
import com.iiooss.ccaallll.ui.dial.activity.DialActivity
import com.iiooss.ccaallll.ui.main.MainActivity
import com.iiooss.ccaallll.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DialFragment : BaseFragment<DialViewModel, DialFragmentBinding>(R.layout.dial_fragment) {

    val viewModel: DialViewModel by viewModel { parametersOf(this) }
    // todo always try to get current call
    override fun setupUI() {
        binding.isBackVisible = requireActivity() !is DialActivity
        (requireActivity() as? DialActivity)?.intent?.data?.schemeSpecificPart?.let(viewModel.text::set)
        binding.buttonCall.setOnClickListener {
            viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
                if (it) {
                    activityAs<BaseActivity<*, * >>().call(viewModel.text.get()!!)
                    (requireActivity() as? CallActivity)?.removeFragment(this)
                }
            }
        }
        binding.buttonBack.setOnClickListener(::onBackPressed)
    }

    private fun onBackPressed() =
        (requireActivity() as? MainActivity)?.onBackPressed() ?:
        (requireActivity() as? CallActivity)?.removeFragment(this)

    override fun provideViewModel() = viewModel

}