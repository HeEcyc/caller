package com.iiooss.ccaallll.ui.language

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.iiooss.ccaallll.R
import com.iiooss.ccaallll.base.BaseFragment
import com.iiooss.ccaallll.databinding.LanguageFragmentBinding
import com.iiooss.ccaallll.utils.setOnClickListener
import org.koin.android.viewmodel.ext.android.viewModel

class LanguageFragment : BaseFragment<LanguageViewModel, LanguageFragmentBinding>(R.layout.language_fragment) {

    val viewModel: LanguageViewModel by viewModel()

    override fun setupUI() {
        binding.buttonBack.setOnClickListener(requireActivity()::onBackPressed)
        binding.recyclerView.addItemDecoration(getNewItemDecoration())
    }

    private fun getNewItemDecoration() = object : RecyclerView.ItemDecoration() {

        private val paint = Paint().apply { color = Color.parseColor("#330A0A0B") }

        private val lineWidth by lazy { binding.root.width / 360f * 346 }
        private val lineHeight by lazy { binding.root.width / 360f * 0.5f }

        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            val isLtr = parent.layoutDirection == View.LAYOUT_DIRECTION_LTR
            for (i in 1 until parent.childCount) {
                c.drawRect(
                    if (isLtr) parent.width - lineWidth else 0f,
                    parent.children.first().height * i - lineHeight / 2,
                    if (isLtr) parent.width.toFloat() else lineWidth,
                    parent.children.first().height * i + lineHeight / 2,
                    paint
                )
            }
        }

    }

    override fun provideViewModel() = viewModel

}