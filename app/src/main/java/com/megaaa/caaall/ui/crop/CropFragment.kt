package com.megaaa.caaall.ui.crop

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.core.os.bundleOf
import com.isseiaoki.simplecropview.callback.CropCallback
import com.megaaa.caaall.R
import com.megaaa.caaall.base.BaseFragment
import com.megaaa.caaall.databinding.CropFragmentBinding
import com.megaaa.caaall.utils.setOnClickListener
import com.nguyenhoanglam.imagepicker.model.Image
import org.koin.android.viewmodel.ext.android.viewModel

class CropFragment : BaseFragment<CropViewModel, CropFragmentBinding>(R.layout.crop_fragment) {

    private val viewModel: CropViewModel by viewModel()

    private val image: Image by lazy { requireArguments().getParcelable(ARGUMENT_IMAGE)!! }

    companion object {
        private const val ARGUMENT_IMAGE = "argument_image"

        fun newInstance(image: Image) = CropFragment().apply {
            arguments = bundleOf(ARGUMENT_IMAGE to (image as Parcelable))
        }
    }

    override fun setupUI() {
        binding.cropView.imageBitmap = viewModel.fileRepository.getBitmap(image.uri, requireContext())
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        binding.buttonSave.setOnClickListener {
            binding.cropView.cropAsync(object : CropCallback {
                override fun onError(e: Throwable) {}
                override fun onSuccess(cropped: Bitmap) {
                    viewModel.createThemeForBitmap(cropped, requireActivity()::onBackPressed)
                }
            })
        }
    }

    override fun provideViewModel() = viewModel

}