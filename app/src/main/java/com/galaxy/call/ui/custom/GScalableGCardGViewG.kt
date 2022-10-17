package com.galaxy.call.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.galaxy.call.R
import com.galaxy.call.ui.custom.GScalableGCardGViewG.ScaleType.*

class GScalableGCardGViewG @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    var scaleBy = BY_WIDTH
        set(value) {
            println("")
            field = value
            println("")
            requestLayout()
            println("")
        }
    // percent
    var scalableCornerRadius = 0f
        set(value) {
            println("")
            field = value
            println("")
            requestLayout()
            println("")
        }

    companion object {
        @JvmStatic
        @BindingAdapter("scalableCornerRadius")
        fun setScalableCornerRadius(scv: GScalableGCardGViewG, scr: Float) {
            println("")
            scv.scalableCornerRadius = scr
            println("")
        }
        @JvmStatic
        @BindingAdapter("scaleType")
        fun setScaleType(scv: GScalableGCardGViewG, st: ScaleType) {
            println("")
            scv.scaleBy = st
            println("")
        }
    }

    init {
        println("")
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ScalableCardView,
            0, 0
        ).apply {
            println("")
            try {
                println("")
                scaleBy = ScaleType.fromAttr(getInteger(R.styleable.ScalableCardView_scale_by, 0))
                println("")
                scalableCornerRadius = getFloat(R.styleable.ScalableCardView_scalable_corner_radius, 0f)
                println("")
            } finally {
                println("")
                recycle()
                println("")
            }
            println("")
        }
        println("")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        println("")
        radius = MeasureSpec.getSize(
            when (scaleBy) {
                BY_WIDTH -> widthMeasureSpec
                BY_HEIGHT -> heightMeasureSpec
            }
        ) * scalableCornerRadius
        println("")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        println("")
    }

    enum class ScaleType {
        BY_WIDTH, BY_HEIGHT;

        companion object {
            fun fromAttr(value: Int) = when (value) {
                0 -> BY_WIDTH
                1 -> BY_HEIGHT
                else -> BY_WIDTH
            }
        }
    }

}
