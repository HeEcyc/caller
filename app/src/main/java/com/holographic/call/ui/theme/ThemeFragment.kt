package com.holographic.call.ui.theme

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.holographic.call.App
import com.holographic.call.R
import com.holographic.call.base.BaseFragment
import com.holographic.call.databinding.ThemeFragmentBinding
import com.holographic.call.ui.contact.ContactFragment
import com.holographic.call.ui.custom.ItemDecorationWithEnds
import com.holographic.call.ui.main.MainActivity
import com.holographic.call.utils.presetThemes
import com.holographic.call.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ThemeFragment : BaseFragment<ThemeViewModel, ThemeFragmentBinding>(R.layout.theme_fragment) {

    val viewModel: ThemeViewModel by viewModel { parametersOf(this) }

    override fun setupUI() {
        binding.root.post {
            val isLtr = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR
            val edgeSpace = binding.root.width * 20 / 360
            val innerSpace = binding.root.width * 6 / 360
            val verticalSpace = binding.root.width * 34 / 360
            val itemDecoration = ItemDecorationWithEnds(
                topFirst = verticalSpace,
                top = verticalSpace,
                topLast = verticalSpace,
                bottomFirst = verticalSpace,
                bottom = verticalSpace,
                bottomLast = verticalSpace,
                leftFirst = if (isLtr) edgeSpace else innerSpace,
                left = innerSpace,
                leftLast = if (isLtr) innerSpace else edgeSpace,
                rightFirst = if (isLtr) innerSpace else edgeSpace,
                right = innerSpace,
                rightLast = if (isLtr) edgeSpace else innerSpace,
                firstPredicate = ::isFirstAdapterItem,
                lastPredicate = ::isLastAdapterItem
            )
            binding.recyclerView.addItemDecoration(itemDecoration)
        }
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        presetThemes.getOrNull(1)?.let {
            Glide.with(App.instance).load(it.previewFile).centerCrop().into(binding.imagePreview)
        }
        viewModel.needRequestLayout.observe(this) {
            binding.buttonApply.requestLayout()
        }
        viewModel.addNewTheme.observe(this) {
            viewModel.permissionRepository.askStoragePermissions(lifecycleScope) {
                if (it) viewModel.addNewTheme()
            }
        }
        binding.buttonApply.setOnClickListener {
            activityAs<MainActivity>().fragment(ContactFragment::class.java)?.viewModel?.setContactTheme(viewModel.selectedTheme.get()!!)
            requireActivity().onBackPressed()
        }
        viewModel.themeSelected.observe(this) {
            Glide.with(App.instance).load(it.previewFile).centerCrop().into(binding.imagePreview)
        }
    }

    private fun isFirstAdapterItem(position: Int) = position == 0

    private fun isLastAdapterItem(position: Int, count: Int) = position == count - 1

    override fun provideViewModel() = viewModel

}