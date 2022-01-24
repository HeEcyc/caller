package com.callerafter.lovelycall.ui.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.databinding.ViewSwitchBinding

class FancySwitch @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val binding: ViewSwitchBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_switch,
        this,
        true
    )

    var isChecked = false
        set(value) {
            if (value != field) {
                animateToValue(value)
                field = value
            }
        }

    companion object {
        private val colorChecked = Color.parseColor("#000000")
        private val colorUnchecked = Color.parseColor("#000000")
    }

    fun setIsChecked(value: Boolean) {
        isChecked = value
    }

    init {
        initAttributes()
    }

    private fun animateToValue(value: Boolean) {
        val constraintSet = ConstraintSet().apply {
            clone(context, if (value) R.layout.view_switch_checked else R.layout.view_switch)
        }
        TransitionManager.beginDelayedTransition(binding.root, ChangeBounds().apply { duration = 125 })
        constraintSet.applyTo(binding.root)
        val colorStart = if (value) colorUnchecked else colorChecked
        val colorEnd = if (value) colorChecked else colorUnchecked
        animateColor(binding.switchThumb::setBackground_color, colorStart, colorEnd)
        animateColor(binding.switchThumb::setLight_color, colorStart, colorEnd)
        animateColor(binding.switchThumb::setDark_color, colorStart, colorEnd)
    }

    private fun animateColor(onUpdate: (Int) -> Unit, colorStart: Int, colorEnd: Int) =
        ValueAnimator.ofArgb(colorStart, colorEnd).apply {
            addUpdateListener {
                val value = it.animatedValue as Int
                onUpdate(value)
                if (value == colorEnd) removeAllUpdateListeners()
            }
            duration = 125
        }.start()

    private fun initAttributes() {
        context
            .theme
            .obtainStyledAttributes(attrs, R.styleable.FancySwitch, 0, 0)
            .apply {
                try {
                    isChecked = getBoolean(R.styleable.FancySwitch_isChecked, false)
                } finally {
                    recycle()
                }
            }
    }

}