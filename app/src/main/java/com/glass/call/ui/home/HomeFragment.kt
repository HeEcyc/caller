package com.glass.call.ui.home

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.glass.call.R
import com.glass.call.base.BaseFragment
import com.glass.call.databinding.HomeFragmentBinding
import com.glass.call.ui.custom.ItemDecorationWithEnds
import com.glass.call.ui.main.PermissionDialog
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: HomeViewModel by viewModel { parametersOf(this) }

    override fun setupUI() {
        binding.topPanel.setOnClickListener {}
        binding.layoutPreset.topPanel.setOnClickListener {}
        binding.root.post {
            val isLtr = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR
            val outerSpace = binding.root.width * 14 / 360
            val innerSpace = binding.root.width * 7 / 360
            var itemDecoration = ItemDecorationWithEnds(
                leftFirst = if (isLtr) outerSpace else innerSpace,
                left = innerSpace,
                leftLast = if (isLtr) innerSpace else outerSpace,
                rightFirst = if (isLtr) innerSpace else outerSpace,
                right = innerSpace,
                rightLast = if (isLtr) outerSpace else innerSpace,
                firstPredicate = { i -> i % 2 == 0 },
                lastPredicate = { i, _ -> i % 2 == 1 }
            )
            binding.layoutCustom.rvCustom.addItemDecoration(itemDecoration)
            itemDecoration = ItemDecorationWithEnds(
                bottom = binding.root.width * 14 / 360,
                bottomLast = binding.root.width * 71 / 360,
                lastPredicate = { i, c -> if (c % 2 == 0) i == c - 1 || i == c - 2 else i == c - 1 }
            )
            binding.layoutCustom.rvCustom.addItemDecoration(itemDecoration)
            itemDecoration = ItemDecorationWithEnds(
                bottom = binding.root.width * 14 / 360,
                bottomLast = binding.root.width * 71 / 360,
                lastPredicate = { i, c -> i == c - 1 }
            )
            binding.layoutPreset.rvPreset1.addItemDecoration(itemDecoration)
            binding.layoutPreset.rvPreset2.addItemDecoration(itemDecoration)
        }
        binding.layoutCustom.buttonAdd.setOnClickListener {
            viewModel.permissionRepository.askStoragePermissions(lifecycleScope) {
                if (it) viewModel.addNewTheme()
            }
        }
        viewModel.onThemeSelected.observe(this) {
            //todo
        }
        binding.layoutPreset.buttonPermission.setOnClickListener {
            PermissionDialog().show(parentFragmentManager)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.hasAllPermissions.set(viewModel.permissionRepository.hasNecessaryPermissions)
    }

    override fun provideViewModel() = viewModel

}