package com.callerafter.lovelycall.ui.home

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.os.postDelayed
import androidx.recyclerview.widget.RecyclerView
import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.base.BaseFragment
import com.callerafter.lovelycall.databinding.HomeFragmentBinding
import com.callerafter.lovelycall.repository.LocaleRepository
import com.callerafter.lovelycall.ui.custom.ItemDecorationWithEnds
import com.callerafter.lovelycall.ui.home.HomeFragment.Mode.*
import com.callerafter.lovelycall.ui.main.MainActivity
import com.callerafter.lovelycall.ui.settings.SettingsFragment
import com.callerafter.lovelycall.utils.setOnClickListener
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment) {

    private val viewModel: HomeViewModel by viewModel { parametersOf(mode) }

    private val mode: Mode by lazy { arguments?.getSerializable(ARGUMENT_MODE) as? Mode ?: DEFAULT }

    companion object {
        private const val ARGUMENT_MODE = "argument_mode"

        fun newInstance(mode: Mode = DEFAULT) = HomeFragment().apply {
            arguments = bundleOf(ARGUMENT_MODE to mode)
        }
    }

    override fun setupUI() {
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
                rightLast = if (isLtr) edgeSpace else innerSpace
            )
            binding.rvCustom.addItemDecoration(itemDecoration)
            binding.rvPopular.addItemDecoration(itemDecoration)
            binding.rvGames.addItemDecoration(itemDecoration)
            binding.rvCats.addItemDecoration(itemDecoration)
            binding.rvMovies.addItemDecoration(itemDecoration)
        }
    }

    private fun initListeners() {
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        binding.buttonCall.setOnClickListener {
//            activityAs<MainActivity>().addFragment()
        }
        binding.buttonSettings.setOnClickListener {
            activityAs<MainActivity>().addFragment(SettingsFragment())
        }
    }

    override fun provideViewModel() = viewModel

    enum class Mode {
        DEFAULT, THEME_PICKER
    }

}