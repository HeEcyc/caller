package com.callerafter.lovelycall.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.callerafter.lovelycall.BR

abstract class BaseFragment<VM : BaseViewModel, B : ViewDataBinding>(@LayoutRes val layoutId: Int) :
    Fragment() {

    protected lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.setVariable(BR.viewModel, provideViewModel())
        binding.lifecycleOwner = this
        return binding.root.also { it.isClickable = true }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    abstract fun setupUI()

    open fun provideViewModel(): VM? = null

    @Suppress("UNCHECKED_CAST")
    protected fun <A : BaseActivity<*, *>>activityAs(): A = requireActivity() as A

}
