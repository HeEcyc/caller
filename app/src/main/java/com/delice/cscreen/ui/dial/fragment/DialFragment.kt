package com.delice.cscreen.ui.dial.fragment

import androidx.lifecycle.lifecycleScope
import com.delice.cscreen.R
import com.delice.cscreen.base.BaseActivity
import com.delice.cscreen.base.BaseFragment
import com.delice.cscreen.databinding.DialFragmentBinding
import com.delice.cscreen.ui.call.activity.CallActivity
import com.delice.cscreen.ui.dial.activity.DialActivity
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