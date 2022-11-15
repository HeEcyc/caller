package com.roobcall.themes.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.roobcall.themes.R

abstract class BaseBottomDialog<T : ViewDataBinding>(private val layout: Int) :
    BottomSheetDialogFragment() {

    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)!!
        return binding.root
    }

    override fun getTheme() = R.style.BottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    abstract fun setupUI()

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun show(fragmentManager: FragmentManager) {
        this.show(fragmentManager, this::class.java.simpleName)
    }

    protected open fun provideViewModel(): BaseViewModel? = null

    @Suppress("UNCHECKED_CAST")
    protected fun <A : BaseActivity<*, *>>activityAs(): A = requireActivity() as A

}
