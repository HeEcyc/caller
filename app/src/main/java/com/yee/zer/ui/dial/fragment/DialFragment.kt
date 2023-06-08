package com.yee.zer.ui.dial.fragment

import androidx.lifecycle.lifecycleScope
import com.yee.zer.R
import com.yee.zer.base.BaseActivity
import com.yee.zer.base.BaseFragment
import com.yee.zer.databinding.DialFragmentBinding
import com.yee.zer.ui.call.activity.CallActivity
import com.yee.zer.ui.dial.activity.DialActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DialFragment : BaseFragment<DialViewModel, DialFragmentBinding>(R.layout.dial_fragment) {

    val viewModel: DialViewModel by viewModel { parametersOf(this) }

    override fun setupUI() {
        (requireActivity() as? DialActivity)?.intent?.data?.schemeSpecificPart?.let(viewModel.text::set)
        binding.buttonCall.setOnClickListener {
            viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
                if (it) {
                    activityAs<BaseActivity<*, *>>().call(viewModel.text.get()!!)
                    (requireActivity() as? CallActivity)?.removeNoneCallFragment(this)
                }
            }
        }
    }

    override fun provideViewModel() = viewModel

}