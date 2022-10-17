package com.galaxy.call.ui.dial.fragment

import androidx.lifecycle.lifecycleScope
import com.galaxy.call.R
import com.galaxy.call.base.GBaseGActivityG
import com.galaxy.call.base.GBaseGFragmentG
import com.galaxy.call.databinding.DialFragmentBinding
import com.galaxy.call.ui.call.activity.GCallGActivityG
import com.galaxy.call.ui.dial.activity.GDialGActivityG
import com.galaxy.call.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GDialGFragmentG : GBaseGFragmentG<GDialGViewGModelG, DialFragmentBinding>(R.layout.dial_fragment) {

    val viewModel: GDialGViewGModelG by viewModel { parametersOf(this) }

    var onButtonClick: (String) -> Unit = {}

    override fun setupUI() {
        println("")
        viewModel.onButtonClickAdditional = onButtonClick
        println("")
        (requireActivity() as? GDialGActivityG)?.intent?.data?.schemeSpecificPart?.let(viewModel.text::set)
        println("")
        binding.buttonCall.setOnClickListener {
            println("")
            viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
                println("")
                if (it) {
                    println("")
                    activityAs<GBaseGActivityG<*, *>>().call(viewModel.text.get()!!)
                    println("")
                    (requireActivity() as? GCallGActivityG)?.removeNoneCallFragment(this)
                    println("")
                }
                println("")
            }
            println("")
        }
        println("")
        binding.buttonBack.setOnClickListener(::onBackPressed)
        println("")
    }

    private fun onBackPressed() {
        println("")
        (requireActivity() as? GCallGActivityG)?.removeNoneCallFragment(this) ?: requireActivity().onBackPressed()
        println("")
    }

    override fun provideViewModel() = viewModel

}