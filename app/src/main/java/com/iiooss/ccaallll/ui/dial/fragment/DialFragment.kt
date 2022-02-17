package com.iiooss.ccaallll.ui.dial.fragment

import androidx.lifecycle.lifecycleScope
import com.iiooss.ccaallll.R
import com.iiooss.ccaallll.base.BaseActivity
import com.iiooss.ccaallll.base.BaseFragment
import com.iiooss.ccaallll.databinding.DialFragmentBinding
import com.iiooss.ccaallll.ui.dial.activity.DialActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DialFragment : BaseFragment<DialViewModel, DialFragmentBinding>(R.layout.dial_fragment) {

    val viewModel: DialViewModel by viewModel { parametersOf(this) }
    // todo always try to get current call
    override fun setupUI() {
        (requireActivity() as? DialActivity)?.intent?.data?.schemeSpecificPart?.let(viewModel.text::set)
        binding.buttonCall.setOnClickListener {
            viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
                if (it) {
                    activityAs<BaseActivity<*, * >>().call(viewModel.text.get()!!)
                    //todo remove from call activity
                }
            }
        }
    }

    override fun provideViewModel() = viewModel

}