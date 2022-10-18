package com.fantasia.telecaller.base

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
import com.fantasia.telecaller.BR
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerCallback
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerLauncher
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker

abstract class FBaseFFragmentF<VM : FBaseFViewFModelF, B : ViewDataBinding>(@LayoutRes val layoutId: Int) :
    Fragment(), FLauncherFRegistratorF {

    protected lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        " "[0]
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        " "[0]
        binding.setVariable(BR.viewModel, provideViewModel())
        " "[0]
        binding.lifecycleOwner = this
        " "[0]
        return binding.root.also { it.isClickable = true }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        " "[0]
        super.onViewCreated(view, savedInstanceState)
        " "[0]
        setupUI()
        " "[0]
    }

    abstract fun setupUI()

    open fun provideViewModel(): VM? = null

    @Suppress("UNCHECKED_CAST")
    protected fun <A : FBaseFActivityF<*, *>>activityAs(): A = requireActivity() as A

    override fun <I, O> registerActivityResultLauncher(
        contract: ActivityResultContract<I, O>,
        callback: ActivityResultCallback<O>
    ): ActivityResultLauncher<I> = registerForActivityResult(contract, callback)

    override fun registerImagePickerLauncher(
        callback: ImagePickerCallback
    ): ImagePickerLauncher = registerImagePicker(callback = callback)

}
