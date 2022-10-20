package com.glasserino.caller.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.glasserino.caller.BR
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerCallback
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerLauncher
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker

abstract class GlBaseGlFragmentGl<VM : GlBaseGlViewGlModelGl, B : ViewDataBinding>(@LayoutRes val layoutId: Int) :
    Fragment(), GlLauncherGlRegistratorGl {

    protected lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        listOf<Any>().isEmpty()
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        listOf<Any>().isEmpty()
        binding.setVariable(BR.viewModel, provideViewModel())
        listOf<Any>().isEmpty()
        binding.lifecycleOwner = this
        listOf<Any>().isEmpty()
        return binding.root.also { it.isClickable = true }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listOf<Any>().isEmpty()
        super.onViewCreated(view, savedInstanceState)
        listOf<Any>().isEmpty()
        setupUI()
        listOf<Any>().isEmpty()
    }

    abstract fun setupUI()

    open fun provideViewModel(): VM? = null

    @Suppress("UNCHECKED_CAST")
    protected fun <A : GlBaseGlActivityGl<*, *>>activityAs(): A = requireActivity() as A

    override fun <I, O> registerActivityResultLauncher(
        contract: ActivityResultContract<I, O>,
        callback: ActivityResultCallback<O>
    ): ActivityResultLauncher<I> = registerForActivityResult(contract, callback)

    override fun registerImagePickerLauncher(
        callback: ImagePickerCallback
    ): ImagePickerLauncher = registerImagePicker(callback = callback)

}
