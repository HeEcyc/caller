package com.galaxy.call.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.galaxy.call.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class GBaseGBottomGDialogG<T : ViewDataBinding>(private val layout: Int) :
    BottomSheetDialogFragment() {

    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("")
        binding = DataBindingUtil.inflate(inflater, layout, container, false)!!
        println("")
        return binding.root
    }

    override fun getTheme() = R.style.BottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        println("")
        super.onViewCreated(view, savedInstanceState)
        println("")
        setupUI()
        println("")
    }

    abstract fun setupUI()

    override fun onStart() {
        println("")
        super.onStart()
        println("")
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        println("")
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        println("")
        dialog?.window?.setLayout(width, height)
        println("")
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        println("")
    }

    protected open fun provideViewModel(): GBaseGViewGModelG? = null

    @Suppress("UNCHECKED_CAST")
    protected fun <A : GBaseGActivityG<*, *>>activityAs(): A = requireActivity() as A

}
