package com.yee.zer.ui.themes

import android.view.View
import com.yee.zer.R
import com.yee.zer.base.BaseActivity
import com.yee.zer.databinding.ThemesActivityBinding
import com.yee.zer.ui.custom.ItemDecorationWithEnds
import com.yee.zer.ui.home.SelectDialog
import com.yee.zer.ui.preview.PreviewActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ThemesActivity : BaseActivity<ThemesViewModel, ThemesActivityBinding>() {

    val viewModel: ThemesViewModel by viewModel { parametersOf(this) }

    override fun provideLayoutId() = R.layout.themes_activity

    override fun setupUI() {
        binding.rv.post {
            val spaceInner = binding.rv.width / 360 * 8
            val spaceOuter = binding.rv.width / 360 * 16
            val isLTR = binding.root.layoutDirection == View.LAYOUT_DIRECTION_LTR
            binding.rv.addItemDecoration(
                ItemDecorationWithEnds(
                    leftFirst = if (isLTR) spaceOuter else spaceInner,
                    leftLast = if (isLTR) spaceInner else spaceOuter,
                    rightFirst = if (isLTR) spaceInner else spaceOuter,
                    rightLast = if (isLTR) spaceOuter else spaceInner,
                    topFirst = spaceInner,
                    topLast = spaceInner,
                    bottomFirst = spaceInner,
                    bottomLast = spaceInner,
                    firstPredicate = { i -> i % 2 == 0 },
                    lastPredicate = { i, _ -> i % 2 != 0 }
                )
            )
        }
        binding.buttonBack.setOnClickListener { finish() }
        viewModel.onThemeSelected.observe(this) { theme ->
            viewModel.permissionRepository.askContactsPermission {
                if (it)
                    startActivity(PreviewActivity.newIntent(theme, this))
            }
        }
    }

    override fun provideViewModel() = viewModel
}