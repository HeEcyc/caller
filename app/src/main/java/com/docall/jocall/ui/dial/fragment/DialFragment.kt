package com.docall.jocall.ui.dial.fragment

import androidx.lifecycle.lifecycleScope
import com.docall.jocall.R
import com.docall.jocall.base.BaseActivity
import com.docall.jocall.base.BaseFragment
import com.docall.jocall.databinding.DialFragmentBinding
import com.docall.jocall.ui.call.activity.CallActivity
import com.docall.jocall.ui.dial.activity.DialActivity
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