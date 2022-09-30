package com.sappy.callthemes.ui.home

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.sappy.callthemes.R
import com.sappy.callthemes.base.BaseFragment
import com.sappy.callthemes.databinding.HomeFragmentBinding
import com.sappy.callthemes.ui.custom.ItemDecorationWithEnds
import com.sappy.callthemes.ui.main.MainActivity
import com.sappy.callthemes.ui.preview.PreviewFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: HomeViewModel by viewModel { parametersOf(this) }

    override fun setupUI() {
        binding.root.post {
            val isLtr = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR
            val outerSpace = binding.recyclerView.width * 20 / 360
            val innerSpace = binding.recyclerView.width * 5 / 360
            var itemDecoration = ItemDecorationWithEnds(
                leftFirst = if (isLtr) outerSpace else innerSpace,
                leftLast = if (isLtr) innerSpace else outerSpace,
                rightFirst = if (isLtr) innerSpace else outerSpace,
                rightLast = if (isLtr) outerSpace else innerSpace,
                firstPredicate = { i -> i % 2 == 0 },
                lastPredicate = { i, _ -> i % 2 == 1 }
            )
            binding.recyclerView.addItemDecoration(itemDecoration)
            val verticalSpace = binding.recyclerView.width * 10 / 360
            val verticalSpaceLast = binding.recyclerView.width * 107 / 360
            itemDecoration = ItemDecorationWithEnds(
                bottom = verticalSpace,
                bottomLast = verticalSpaceLast,
                lastPredicate = { i, c -> if (c % 2 == 0) i in (c-2)..(c-1) else i == c - 1 }
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