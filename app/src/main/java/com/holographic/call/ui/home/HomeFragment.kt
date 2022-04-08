package com.holographic.call.ui.home

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.holographic.call.App
import com.holographic.call.R
import com.holographic.call.base.BaseFragment
import com.holographic.call.databinding.HomeFragmentBinding
import com.holographic.call.ui.contacts.ContactsFragment
import com.holographic.call.ui.custom.ItemDecorationWithEnds
import com.holographic.call.ui.main.MainActivity
import com.holographic.call.ui.settings.SettingsFragment
import com.holographic.call.utils.themesPopular
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: HomeViewModel by viewModel { parametersOf(this) }

    override fun setupUI() {
        binding.root.post {
            val isLtr = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR
            val edgeSpace = binding.root.width * 20 / 360
            val innerSpace = binding.root.width * 6 / 360
            val verticalSpace = binding.root.width * 34 / 360
            val itemDecoration = ItemDecorationWithEnds(
                topFirst = verticalSpace,
                top = verticalSpace,
                topLast = verticalSpace,
                bottomFirst = verticalSpace,
                bottom = verticalSpace,
                bottomLast = verticalSpace,
                leftFirst = if (isLtr) edgeSpace else innerSpace,
                left = innerSpace,
                leftLast = if (isLtr) innerSpace else edgeSpace,
                rightFirst = if (isLtr) innerSpace else edgeSpace,
                right = innerSpace,
                rightLast = if (isLtr) edgeSpace else innerSpace,
                firstPredicate = ::isFirstAdapterItem,
                lastPredicate = ::isLastAdapterItem
            )
            binding.recyclerView.addItemDecoration(itemDecoration)
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
        binding.buttonContacts.setOnClickListener {
            viewModel.permissionRepository.askContactsPermission {
                if (it) activityAs<MainActivity>().addFragment(ContactsFragment.newInstance())
            }
        }
        binding.buttonSettings.setOnClickListener {
            activityAs<MainActivity>().addFragment(SettingsFragment())
        }
        binding.buttonApply.setOnClickListener {
            viewModel.permissionRepository.askContactsPermission {
                if (it) activityAs<MainActivity>().addFragment(ContactsFragment.newInstance(ContactsFragment.Mode.CONTACT_SELECTOR))
            }
        }
        viewModel.themeSelected.observe(this) {
            Glide.with(App.instance).load(it.previewFile).centerCrop().into(binding.imagePreview)
        }
    }

    private fun isFirstAdapterItem(position: Int) = position == 0

    private fun isLastAdapterItem(position: Int, count: Int) = position == count - 1

    override fun provideViewModel() = viewModel

}