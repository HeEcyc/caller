package com.galala.lalaxy.ui.dial.fragment

import androidx.lifecycle.lifecycleScope
import com.galala.lalaxy.R
import com.galala.lalaxy.base.GBaseGActivityG
import com.galala.lalaxy.base.GBaseGFragmentG
import com.galala.lalaxy.databinding.DialFragmentBinding
import com.galala.lalaxy.ui.call.activity.GCallGActivityG
import com.galala.lalaxy.ui.dial.activity.GDialGActivityG
import com.galala.lalaxy.utils.setOnClickListener
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