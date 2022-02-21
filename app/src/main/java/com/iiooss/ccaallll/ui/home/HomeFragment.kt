package com.iiooss.ccaallll.ui.home

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.iiooss.ccaallll.App
import com.iiooss.ccaallll.R
import com.iiooss.ccaallll.base.BaseFragment
import com.iiooss.ccaallll.databinding.HomeFragmentBinding
import com.iiooss.ccaallll.ui.contacts.ContactsFragment
import com.iiooss.ccaallll.ui.custom.ItemDecorationWithEnds
import com.iiooss.ccaallll.ui.main.MainActivity
import com.iiooss.ccaallll.ui.settings.SettingsFragment
import com.iiooss.ccaallll.utils.themesPopular
import io.github.florent37.shapeofview.shapes.RoundRectView
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment) {

    val viewModel: HomeViewModel by viewModel { parametersOf(this) }

    private val cornerRadius by lazy { binding.themesContainer.width / 360f * 24 }
    private val themesBackground by lazy { binding.themesBackground }

    override fun setupUI() {
        binding.themesContainer.setOnClickListener {}
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
            themesBackground.setRadius(cornerRadius)
        }
        binding.motionLayout.addTransitionListener(newTransactionListener())
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

    private fun RoundRectView.setRadius(radius: Float) {
        topLeftRadius = radius
        topRightRadius = radius
    }

    private fun animateCorners(from: Float, to: Float) {
        if (themesBackground.topLeftRadius != to)
            ValueAnimator.ofFloat(from, to).apply {
                interpolator = LinearInterpolator()
                duration = 250
                addUpdateListener { themesBackground.setRadius(it.animatedValue as Float) }
            }.start()
    }

    private fun roundCorners() = animateCorners(0f, cornerRadius)

    private fun sharpenCorners() = animateCorners(cornerRadius, 0f)

    override fun provideViewModel() = viewModel

    private fun newTransactionListener() = object : MotionLayout.TransitionListener {
        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            roundCorners()
        }
        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}
        override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            if (p1 == R.id.end) sharpenCorners()
        }
        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
    }

}