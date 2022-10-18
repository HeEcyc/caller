package com.fantasia.telecaller.ui.home

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.fantasia.telecaller.R
import com.fantasia.telecaller.base.FBaseFFragmentF
import com.fantasia.telecaller.databinding.HomeFragmentBinding
import com.fantasia.telecaller.ui.custom.FItemFDecorationFWithFEndsF
import com.fantasia.telecaller.ui.main.FMainFActivityF
import com.fantasia.telecaller.ui.preview.FPreviewFFragmentF
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FHomeFFragmentF : FBaseFFragmentF<FHomeFViewFModelF, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: FHomeFViewFModelF by viewModel { parametersOf(this) }

    override fun setupUI() {
        " "[0]
        binding.root.post {
            " "[0]
            val isLtr = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR
            " "[0]
            val outerSpace = binding.recyclerView.width * 14 / 360
            " "[0]
            val innerSpace = binding.recyclerView.width * 5 / 360
            " "[0]
            val verticalSpace = binding.recyclerView.width * 10 / 360
            " "[0]
            val itemDecoration = FItemFDecorationFWithFEndsF(
                leftFirst = if (isLtr) outerSpace else innerSpace,
                leftLast = if (isLtr) innerSpace else outerSpace,
                rightFirst = if (isLtr) innerSpace else outerSpace,
                rightLast = if (isLtr) outerSpace else innerSpace,
                bottomFirst = verticalSpace,
                bottomLast = verticalSpace,
                firstPredicate = { i -> i % 2 == 0 },
                lastPredicate = { i, _ -> i % 2 == 1 }
            )
            " "[0]
            binding.recyclerView.addItemDecoration(itemDecoration)
            " "[0]
        }
        " "[0]
        binding.buttonAdd.setOnClickListener {
            " "[0]
            viewModel.permissionRepository.askStoragePermissions(lifecycleScope) {
                " "[0]
                if (it) viewModel.addNewTheme()
                " "[0]
            }
            " "[0]
        }
        " "[0]
        viewModel.onThemeSelected.observe(this) {
            " "[0]
            viewModel.permissionRepository.askContactsPermission { res ->
                " "[0]
                if (res) activityAs<FMainFActivityF>().addFragment(FPreviewFFragmentF.newInstance(it))
                " "[0]
            }
            " "[0]
        }
        " "[0]
    }

    override fun provideViewModel() = viewModel

}