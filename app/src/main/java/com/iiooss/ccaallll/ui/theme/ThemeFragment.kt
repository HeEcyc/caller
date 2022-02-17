package com.iiooss.ccaallll.ui.theme

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.iiooss.ccaallll.R
import com.iiooss.ccaallll.base.BaseFragment
import com.iiooss.ccaallll.databinding.ThemeFragmentBinding
import com.iiooss.ccaallll.ui.contact.ContactFragment
import com.iiooss.ccaallll.ui.custom.ItemDecorationWithEnds
import com.iiooss.ccaallll.ui.main.MainActivity
import com.iiooss.ccaallll.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ThemeFragment : BaseFragment<ThemeViewModel, ThemeFragmentBinding>(R.layout.theme_fragment) {

    val viewModel: ThemeViewModel by viewModel { parametersOf(this) }

    override fun setupUI() {
        binding.root.post {
            val isLtr = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR
            val edgeSpace = binding.root.width * 28 / 360
            val innerSpace = binding.root.width * 5 / 360
            val itemDecoration = ItemDecorationWithEnds(
                leftFirst = if (isLtr) edgeSpace else innerSpace,
                left = innerSpace,
                leftLast = if (isLtr) innerSpace else edgeSpace,
                rightFirst = if (isLtr) innerSpace else edgeSpace,
                right = innerSpace,
                rightLast = if (isLtr) edgeSpace else innerSpace,
                firstPredicate = ::isFirstAdapterItem,
                lastPredicate = ::isLastAdapterItem
            )
            binding.rvCustom.addItemDecoration(itemDecoration)
            binding.rvPopular.addItemDecoration(itemDecoration)
            binding.rvGames.addItemDecoration(itemDecoration)
            binding.rvCats.addItemDecoration(itemDecoration)
            binding.rvMovies.addItemDecoration(itemDecoration)
        }
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        viewModel.addNewTheme.observe(this) {
            viewModel.permissionRepository.askStoragePermissions(lifecycleScope) {
                if (it) viewModel.addNewTheme()
            }
        }
        viewModel.setTheme.observe(this) {
            activityAs<MainActivity>().fragment(ContactFragment::class.java)?.viewModel?.setContactTheme(it)
            requireActivity().onBackPressed()
        }
    }

    private fun isFirstAdapterItem(position: Int) = position == 0

    private fun isLastAdapterItem(position: Int, count: Int) = position == count - 1

    override fun provideViewModel() = viewModel

}