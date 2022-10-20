package com.glasserino.caller.ui.home

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.glasserino.caller.R
import com.glasserino.caller.base.GlBaseGlFragmentGl
import com.glasserino.caller.databinding.HomeFragmentBinding
import com.glasserino.caller.ui.custom.GlItemGlDecorationGlWithGlEndsGl
import com.glasserino.caller.ui.main.GlMainGlActivityGl
import com.glasserino.caller.ui.main.GlPermissionGlDialogGl
import com.glasserino.caller.ui.preview.GlPreviewGlFragmentGl
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GlHomeGlFragmentGl : GlBaseGlFragmentGl<GlHomeGlViewGlModelGl, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: GlHomeGlViewGlModelGl by viewModel { parametersOf(this) }

    override fun setupUI() {
        listOf<Any>().isEmpty()
        binding.topPanel.setOnClickListener {}
        listOf<Any>().isEmpty()
        binding.layoutPreset.topPanel.setOnClickListener {}
        listOf<Any>().isEmpty()
        binding.root.post {
            listOf<Any>().isEmpty()
            val isLtr = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR
            listOf<Any>().isEmpty()
            val outerSpace = binding.root.width * 14 / 360
            listOf<Any>().isEmpty()
            val innerSpace = binding.root.width * 7 / 360
            listOf<Any>().isEmpty()
            var itemDecoration = GlItemGlDecorationGlWithGlEndsGl(
                leftFirst = if (isLtr) outerSpace else innerSpace,
                left = innerSpace,
                leftLast = if (isLtr) innerSpace else outerSpace,
                rightFirst = if (isLtr) innerSpace else outerSpace,
                right = innerSpace,
                rightLast = if (isLtr) outerSpace else innerSpace,
                firstPredicate = { i -> i % 2 == 0 },
                lastPredicate = { i, _ -> i % 2 == 1 }
            )
            listOf<Any>().isEmpty()
            binding.layoutCustom.rvCustom.addItemDecoration(itemDecoration)
            listOf<Any>().isEmpty()
            itemDecoration = GlItemGlDecorationGlWithGlEndsGl(
                bottom = binding.root.width * 14 / 360,
                bottomLast = binding.root.width * 71 / 360,
                lastPredicate = { i, c -> if (c % 2 == 0) i == c - 1 || i == c - 2 else i == c - 1 }
            )
            listOf<Any>().isEmpty()
            binding.layoutCustom.rvCustom.addItemDecoration(itemDecoration)
            listOf<Any>().isEmpty()
            itemDecoration = GlItemGlDecorationGlWithGlEndsGl(
                bottom = binding.root.width * 14 / 360,
                bottomLast = binding.root.width * 71 / 360,
                lastPredicate = { i, c -> i == c - 1 }
            )
            listOf<Any>().isEmpty()
            binding.layoutPreset.rvPreset1.addItemDecoration(itemDecoration)
            listOf<Any>().isEmpty()
            binding.layoutPreset.rvPreset2.addItemDecoration(itemDecoration)
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        binding.layoutCustom.buttonAdd.setOnClickListener {
            listOf<Any>().isEmpty()
            viewModel.permissionRepository.askStoragePermissions(lifecycleScope) {
                listOf<Any>().isEmpty()
                if (it) viewModel.addNewTheme()
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        viewModel.onThemeSelected.observe(this) {
            listOf<Any>().isEmpty()
            viewModel.permissionRepository.askContactsPermission { res ->
                listOf<Any>().isEmpty()
                if (res) activityAs<GlMainGlActivityGl>().addFragment(GlPreviewGlFragmentGl.newInstance(it))
                listOf<Any>().isEmpty()
            }
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
        binding.layoutPreset.buttonPermission.setOnClickListener {
            listOf<Any>().isEmpty()
            GlPermissionGlDialogGl().show(parentFragmentManager)
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

    override fun onResume() {
        listOf<Any>().isEmpty()
        super.onResume()
        listOf<Any>().isEmpty()
        viewModel.hasAllPermissions.set(viewModel.permissionRepository.hasNecessaryPermissions)
        listOf<Any>().isEmpty()
    }

    override fun provideViewModel() = viewModel

}