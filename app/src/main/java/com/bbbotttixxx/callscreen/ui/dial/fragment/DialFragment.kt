package com.bbbotttixxx.callscreen.ui.dial.fragment

import androidx.lifecycle.lifecycleScope
import com.bbbotttixxx.callscreen.R
import com.bbbotttixxx.callscreen.base.BaseActivity
import com.bbbotttixxx.callscreen.base.BaseFragment
import com.bbbotttixxx.callscreen.databinding.DialFragmentBinding
import com.bbbotttixxx.callscreen.ui.call.activity.CallActivity
import com.bbbotttixxx.callscreen.ui.dial.activity.DialActivity
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