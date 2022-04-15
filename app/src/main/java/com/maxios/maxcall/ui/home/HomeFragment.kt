package com.maxios.maxcall.ui.home

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.maxios.maxcall.App
import com.maxios.maxcall.R
import com.maxios.maxcall.base.BaseFragment
import com.maxios.maxcall.databinding.HomeFragmentBinding
import com.maxios.maxcall.ui.custom.ItemDecorationWithEnds
import com.maxios.maxcall.ui.main.MainActivity
import com.maxios.maxcall.utils.themesPopular
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: HomeViewModel by viewModel { parametersOf(this) }

    override fun setupUI() {
        binding.themesContainer.setOnClickListener {}
        binding.root.post {
            val isLtr = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR
            val edgeSpace = binding.root.width * 14 / 360
            val innerSpace = binding.root.width * 8 / 360
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
        themesPopular.getOrNull(1)?.let {
            Glide.with(App.instance).load(it.previewFile).centerCrop().into(binding.imagePreview)
        }
        viewModel.needRequestLayout.observe(this) {
            binding.buttonApply.requestLayout()
        }
        viewModel.addNew.observe(this) {
            viewModel.permissionRepository.askStoragePermissions(lifecycleScope) {
                if (it) viewModel.addNewTheme()
            }
        }
//        binding.buttonApply.setOnClickListener {
//            viewModel.permissionRepository.askContactsPermission {
//                if (it) activityAs<MainActivity>().addFragment(ContactsFragment.newInstance(ContactsFragment.Mode.CONTACT_SELECTOR))
//            }
//        } todo
        viewModel.themeSelected.observe(this) {
            Glide.with(App.instance).load(it.previewFile).centerCrop().into(binding.imagePreview)
        }
    }

    private fun isFirstAdapterItem(position: Int) = position == 0

    private fun isLastAdapterItem(position: Int, count: Int) = position == count - 1

    override fun provideViewModel() = viewModel

}