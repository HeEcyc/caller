package com.fancy.call.ui.home

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.fancy.call.R
import com.fancy.call.base.BaseFragment
import com.fancy.call.databinding.HomeFragmentBinding
import com.fancy.call.ui.custom.ItemDecorationWithEnds
import com.fancy.call.ui.main.MainActivity
import com.fancy.call.ui.preview.PreviewFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: HomeViewModel by viewModel { parametersOf(this) }

    override fun setupUI() {
        binding.root.post {
            val isLtr = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR
            val outerSpace = binding.recyclerView.width * 16 / 360
            val innerSpace = binding.recyclerView.width * 5 / 360
            val verticalSpace = binding.recyclerView.width * 10 / 360
            val itemDecoration = ItemDecorationWithEnds(
                leftFirst = if (isLtr) outerSpace else innerSpace,
                leftLast = if (isLtr) innerSpace else outerSpace,
                rightFirst = if (isLtr) innerSpace else outerSpace,
                rightLast = if (isLtr) outerSpace else innerSpace,
                bottomFirst = verticalSpace,
                bottomLast = verticalSpace,
                firstPredicate = { i -> i % 2 == 0 },
                lastPredicate = { i, _ -> i % 2 == 1 }
            )
            binding.recyclerView.addItemDecoration(itemDecoration)
        }
        binding.buttonAdd.setOnClickListener {
            viewModel.permissionRepository.askStoragePermissions(lifecycleScope) {
                if (it) viewModel.addNewTheme()
            }
        }
        viewModel.onThemeSelected.observe(this) {
            viewModel.permissionRepository.askContactsPermission { res ->
                if (res) activityAs<MainActivity>().addFragment(PreviewFragment.newInstance(it))
            }
        }
    }

    override fun provideViewModel() = viewModel

}