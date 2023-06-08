package com.fusiecal.ler.ui.home

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.fusiecal.ler.R
import com.fusiecal.ler.base.BaseFragment
import com.fusiecal.ler.databinding.HomeFragmentBinding
import com.fusiecal.ler.ui.custom.ItemDecorationWithEnds
import com.fusiecal.ler.ui.preview.PreviewActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: HomeViewModel by viewModel { parametersOf(this) }

    override fun setupUI() {
        binding.root.post {
            val isLTR = binding.root.layoutDirection == View.LAYOUT_DIRECTION_LTR
            val spaceInner = binding.root.width * 8 / 360
            val spaceOuter = binding.root.width * 16 / 360
            val spaceVertical = binding.root.width * 10 / 360
            val itemDecoration = ItemDecorationWithEnds(
                top = spaceVertical,
                topFirst = spaceVertical,
                topLast = spaceVertical,
                leftFirst = if (isLTR) spaceOuter else spaceInner,
                leftLast = if (isLTR) spaceInner else spaceOuter,
                rightLast = if (isLTR) spaceOuter else spaceInner,
                rightFirst = if (isLTR) spaceInner else spaceOuter,
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
        viewModel.onThemeClick.observe(this) {
            viewModel.permissionRepository.askContactsPermission { res ->
                if (res) viewModel.applyToAll(it)
            }
        }
        binding.switchPower.isChecked = viewModel.permissionRepository.hasCallerPermission
        binding.buttonPower.setOnClickListener {
            with(viewModel.permissionRepository) {
                if (hasCallerPermission)
                    openDefaultPhoneSelection(requireContext())
                else
                    askCallerPermission {}
            }
        }
        viewModel.onPreviewClick.observe(this) {
            startActivity(PreviewActivity.newIntent(it, requireContext()))
        }
        binding.menu.setOnClickListener {}
        binding.buttonSettings.setOnClickListener { binding.menu.visibility = View.VISIBLE }
        binding.buttonBackSettings.setOnClickListener { binding.menu.visibility = View.GONE }
        binding.buttonLanguage.setOnClickListener {
            binding.menuSettings.visibility = View.GONE
            binding.headerButtonSettings.visibility = View.GONE
            binding.headerTextSettings.visibility = View.GONE
            binding.menuLanguage.visibility = View.VISIBLE
            binding.headerButtonLanguage.visibility = View.VISIBLE
            binding.headerTextLanguage.visibility = View.VISIBLE
        }
        binding.buttonBackLanguage.setOnClickListener {
            binding.menuLanguage.visibility = View.GONE
            binding.headerButtonLanguage.visibility = View.GONE
            binding.headerTextLanguage.visibility = View.GONE
            binding.menuSettings.visibility = View.VISIBLE
            binding.headerButtonSettings.visibility = View.VISIBLE
            binding.headerTextSettings.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        binding.switchPower.isChecked = viewModel.permissionRepository.hasCallerPermission
        viewModel.adapterRV.getData().forEach {
            it.isSelected.set(it.theme == viewModel.preferencesRepository.theme)
        }
    }

    override fun provideViewModel() = viewModel

}