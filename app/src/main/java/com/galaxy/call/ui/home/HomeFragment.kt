package com.galaxy.call.ui.home

import android.provider.Settings
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import com.galaxy.call.R
import com.galaxy.call.base.BaseFragment
import com.galaxy.call.databinding.HomeFragmentBinding
import com.galaxy.call.model.theme.Theme
import com.galaxy.call.ui.custom.ItemDecorationWithEnds
import com.galaxy.call.ui.main.MainActivity
import com.galaxy.call.ui.main.PermissionDialog
import com.galaxy.call.ui.preview.PreviewFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: HomeViewModel by viewModel { parametersOf(this) }
    var homeAction: (() -> Unit)? = null

    override fun setupUI() {
        setFragmentResultListener("home") { key, _ ->
            if (key == "home") {
                homeAction?.invoke()
                homeAction = null
            }
        }
        binding.root.post {
            val isLtr = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR
            val outerSpace = binding.recyclerView.width * 14 / 360
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
            homeAction = pickImage()
            check()
        }
        viewModel.onThemeSelected.observe(this) {
            homeAction = showSetupTheme(it)
            check()
        }
    }

    private fun pickImage(): () -> Unit = {
        viewModel.permissionRepository.askStoragePermissions(lifecycleScope) {
            if (it) viewModel.addNewTheme()
        }
    }

    private fun check() {
        if (Settings.canDrawOverlays(requireContext())) homeAction?.invoke()
        else showPermissionDialog()
    }

    private fun showSetupTheme(it: Theme): () -> Unit = {
        viewModel.permissionRepository.askContactsPermission { res ->
            if (res) activityAs<MainActivity>().addFragment(PreviewFragment.newInstance(it))
        }
    }

    override fun provideViewModel() = viewModel

    private fun showPermissionDialog() {
        PermissionDialog.newInstance("home")
            .show(parentFragmentManager, "tag")
    }

}