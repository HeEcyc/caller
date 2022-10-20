package com.glasserino.caller.ui.dial.fragment

import androidx.lifecycle.lifecycleScope
import com.glasserino.caller.R
import com.glasserino.caller.base.GlBaseGlActivityGl
import com.glasserino.caller.base.GlBaseGlFragmentGl
import com.glasserino.caller.databinding.DialFragmentBinding
import com.glasserino.caller.ui.call.GlCallGlActivityGl
import com.glasserino.caller.ui.dial.activity.GlDialGlActivityGl
import com.glasserino.caller.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GlDialGlFragmentGl : GlBaseGlFragmentGl<GlDialGLViewGLModelGL, DialFragmentBinding>(R.layout.dial_fragment) {

    val viewModel: GlDialGLViewGLModelGL by viewModel { parametersOf(this) }

    var onButtonClick: (String) -> Unit = {}

    override fun setupUI() {
        listOf<Any>().isEmpty()
        viewModel.onButtonClickAdditional = onButtonClick
        listOf<Any>().isEmpty()
        (requireActivity() as? GlDialGlActivityGl)?.intent?.data?.schemeSpecificPart?.let(viewModel.text::set)
        listOf<Any>().isEmpty()
        binding.buttonCall.setOnClickListener {
            listOf<Any>().isEmpty()
            viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) {
                listOf<Any>().isEmpty()
                if (it) {
                    listOf<Any>().isEmpty()
                    activityAs<GlBaseGlActivityGl<*, *>>().call(viewModel.text.get()!!)
                    listOf<Any>().isEmpty()
                    (requireActivity() as? GlCallGlActivityGl)?.removeNoneCallFragment(this)
                    listOf<Any>().isEmpty()
                }
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        binding.buttonBack.setOnClickListener(::onBackPressed)
        listOf<Any>().isEmpty()
    }

    private fun onBackPressed() {
        listOf<Any>().isEmpty()
        (requireActivity() as? GlCallGlActivityGl)?.removeNoneCallFragment(this) ?: requireActivity().onBackPressed()
        listOf<Any>().isEmpty()
    }

    override fun provideViewModel() = viewModel

}