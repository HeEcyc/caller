package com.megaaa.caaall.ui.dial

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.megaaa.caaall.R
import com.megaaa.caaall.base.BaseActivity
import com.megaaa.caaall.databinding.DialActivityBinding
import com.megaaa.caaall.repository.PermissionRepository
import org.koin.android.viewmodel.ext.android.viewModel

class DialActivity : BaseActivity<DialViewModel, DialActivityBinding>() {

    val viewModel: DialViewModel by viewModel()

    override fun provideLayoutId() = R.layout.dial_activity

    override fun setupUI() {
        viewModel.permissionRepository = PermissionRepository(this)
        binding.keyboard.binding.buttonCall.visibility = View.VISIBLE
        binding.keyboard.binding.textView.text = intent?.data?.schemeSpecificPart
        binding.keyboard.binding.buttonCall.setOnClickListener {
            viewModel.permissionRepository.askOutgoingCallPermissions(lifecycleScope) { res ->
                if (res) call(binding.keyboard.binding.textView.text.toString())
            }
        }
    }

    override fun provideViewModel() = viewModel

}