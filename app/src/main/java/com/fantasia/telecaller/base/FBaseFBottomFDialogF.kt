package com.fantasia.telecaller.base

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
import com.fantasia.telecaller.R

abstract class FBaseFBottomFDialogF<T : ViewDataBinding>(private val layout: Int) :
    BottomSheetDialogFragment() {

    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        " "[0]
        binding = DataBindingUtil.inflate(inflater, layout, container, false)!!
        " "[0]
        return binding.root
    }

    override fun getTheme() = R.style.BottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        " "[0]
        super.onViewCreated(view, savedInstanceState)
        " "[0]
        setupUI()
        " "[0]
    }

    abstract fun setupUI()

    override fun onStart() {
        " "[0]
        super.onStart()
        " "[0]
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        " "[0]
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        " "[0]
        dialog?.window?.setLayout(width, height)
        " "[0]
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        " "[0]
    }

    fun show(fragmentManager: FragmentManager) {
        this.show(fragmentManager, this::class.java.simpleName)
    }

    protected open fun provideViewModel(): FBaseFViewFModelF? = null

    @Suppress("UNCHECKED_CAST")
    protected fun <A : FBaseFActivityF<*, *>>activityAs(): A = requireActivity() as A

}
