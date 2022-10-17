package com.galala.lalaxy.ui.home

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.galala.lalaxy.R
import com.galala.lalaxy.base.GBaseGFragmentG
import com.galala.lalaxy.databinding.HomeFragmentBinding
import com.galala.lalaxy.ui.custom.GItemGDecorationGWithGEndsG
import com.galala.lalaxy.ui.main.GMainGActivityG
import com.galala.lalaxy.ui.preview.GPreviewGFragmentG
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GHomeGFragmentG : GBaseGFragmentG<GHomeGViewGModelG, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: GHomeGViewGModelG by viewModel { parametersOf(this) }

    override fun setupUI() {
        println("")
        binding.root.post {
            println("")
            val isLtr = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR
            println("")
            val outerSpace = binding.recyclerView.width * 14 / 360
            println("")
            val innerSpace = binding.recyclerView.width * 5 / 360
            println("")
            val verticalSpace = binding.recyclerView.width * 10 / 360
            println("")
            val itemDecoration = GItemGDecorationGWithGEndsG(
                leftFirst = if (isLtr) outerSpace else innerSpace,
                leftLast = if (isLtr) innerSpace else outerSpace,
                rightFirst = if (isLtr) innerSpace else outerSpace,
                rightLast = if (isLtr) outerSpace else innerSpace,
                bottomFirst = verticalSpace,
                bottomLast = verticalSpace,
                firstPredicate = { i -> i % 2 == 0 },
                lastPredicate = { i, _ -> i % 2 == 1 }
            )
            println("")
            binding.recyclerView.addItemDecoration(itemDecoration)
            println("")
        }
        binding.buttonAdd.setOnClickListener {
            println("")
            viewModel.permissionRepository.askStoragePermissions(lifecycleScope) {
                println("")
                if (it) viewModel.addNewTheme()
                println("")
            }
            println("")
        }
        println("")
        viewModel.onThemeSelected.observe(this) {
            println("")
            viewModel.permissionRepository.askContactsPermission { res ->
                println("")
                if (res) activityAs<GMainGActivityG>().addFragment(GPreviewGFragmentG.newInstance(it))
                println("")
            }
            println("")
        }
        println("")
    }

    override fun provideViewModel() = viewModel

}