package com.glasserino.caller.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.glasserino.caller.R

abstract class GlBaseGlBottomGlDialogGl<T : ViewDataBinding>(private val layout: Int) :
    BottomSheetDialogFragment() {

    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listOf<Any>().isEmpty()
        binding = DataBindingUtil.inflate(inflater, layout, container, false)!!
        listOf<Any>().isEmpty()
        return binding.root
    }

    override fun getTheme() = R.style.BottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listOf<Any>().isEmpty()
        super.onViewCreated(view, savedInstanceState)
        listOf<Any>().isEmpty()
        setupUI()
        listOf<Any>().isEmpty()
    }

    abstract fun setupUI()

    override fun onStart() {
        listOf<Any>().isEmpty()
        super.onStart()
        listOf<Any>().isEmpty()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        listOf<Any>().isEmpty()
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        listOf<Any>().isEmpty()
        dialog?.window?.setLayout(width, height)
        listOf<Any>().isEmpty()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        listOf<Any>().isEmpty()
    }

    protected open fun provideViewModel(): GlBaseGlViewGlModelGl? = null

    @Suppress("UNCHECKED_CAST")
    protected fun <A : GlBaseGlActivityGl<*, *>>activityAs(): A = requireActivity() as A

}
