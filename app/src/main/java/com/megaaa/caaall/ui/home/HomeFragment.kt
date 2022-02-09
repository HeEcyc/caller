package com.megaaa.caaall.ui.home

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.megaaa.caaall.R
import com.megaaa.caaall.base.BaseActivity
import com.megaaa.caaall.base.BaseFragment
import com.megaaa.caaall.databinding.HomeFragmentBinding
import com.megaaa.caaall.repository.ImagePickerRepository
import com.megaaa.caaall.ui.contact.ContactFragment
import com.megaaa.caaall.ui.contacts.ContactsFragment
import com.megaaa.caaall.ui.crop.CropFragment
import com.megaaa.caaall.ui.custom.ItemDecorationWithEnds
import com.megaaa.caaall.ui.home.HomeFragment.Mode.DEFAULT
import com.megaaa.caaall.ui.home.HomeFragment.Mode.THEME_PICKER
import com.megaaa.caaall.ui.main.MainActivity
import com.megaaa.caaall.ui.main.MainViewModel
import com.megaaa.caaall.ui.settings.SettingsFragment
import com.megaaa.caaall.ui.theme.ThemeFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment) {

    private val viewModel: HomeViewModel by viewModel { parametersOf(mode) }
    private val mainViewModel: MainViewModel by activityViewModels()

    private val mode: Mode by lazy { arguments?.getSerializable(ARGUMENT_MODE) as? Mode ?: DEFAULT }

    companion object {
        private const val ARGUMENT_MODE = "argument_mode"

        fun newInstance(mode: Mode = DEFAULT) = HomeFragment().apply {
            arguments = bundleOf(ARGUMENT_MODE to mode)
        }
    }

    override fun setupUI() {
        viewModel.imagePickerRepository = ImagePickerRepository(this)
        initListeners()
        binding.root.post {
            val isLtr = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR
            val edgeSpace = binding.root.width * 16 / 360
            val innerSpace = binding.root.width * 4 / 360
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
    }

    private fun isFirstAdapterItem(position: Int) = position == 0

    private fun isLastAdapterItem(position: Int, count: Int) = position == count - 1

    private fun initListeners() {
        binding.buttonBack.setOnClickListener {
            with(activityAs<BaseActivity<*,*>>()) {
                if (mode == THEME_PICKER) fragment(ContactFragment::class.java)?.setVideoTheme()
                onBackPressed()
            }
        }
        binding.buttonCall.setOnClickListener {
            activityAs<MainActivity>().addFragment(ContactsFragment.newInstance(ContactsFragment.Mode.DEFAULT))
        }
        binding.buttonSettings.setOnClickListener {
            activityAs<MainActivity>().addFragment(SettingsFragment())
        }
        viewModel.onNewThemeClick.observe(this) {
            mainViewModel.permissionRepository.askStoragePermissions(lifecycleScope) {
                if (it)
                    viewModel.imagePickerRepository.pickImage { image ->
                        activityAs<MainActivity>().addFragment(CropFragment.newInstance(image))
                    }
            }
        }
        viewModel.onThemeClick.observe(this) {
            if (mode == THEME_PICKER)
                with(activityAs<MainActivity>()) {
                    fragment(ContactFragment::class.java)?.viewModel?.applyTheme(it)
                    removeFragment(this@HomeFragment)
                }
            else
                activityAs<MainActivity>().addFragment(ThemeFragment.newInstance(it))
        }
    }

    override fun provideViewModel() = viewModel

    enum class Mode {
        DEFAULT, THEME_PICKER
    }

}