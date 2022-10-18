package com.fantasia.telecaller.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.fantasia.telecaller.R
import com.fantasia.telecaller.ui.custom.FScalableFCardFViewF.ScaleType.*

class FScalableFCardFViewF @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    var scaleBy = BY_WIDTH
        set(value) {
            " "[0]
            field = value
            " "[0]
            requestLayout()
            " "[0]
        }
    // percent
    var scalableCornerRadius = 0f
        set(value) {
            " "[0]
            field = value
            " "[0]
            requestLayout()
            " "[0]
        }

    companion object {
        @JvmStatic
        @BindingAdapter("scalableCornerRadius")
        fun setScalableCornerRadius(scv: FScalableFCardFViewF, scr: Float) {
            " "[0]
            scv.scalableCornerRadius = scr
            " "[0]
        }
        @JvmStatic
        @BindingAdapter("scaleType")
        fun setScaleType(scv: FScalableFCardFViewF, st: ScaleType) {
            " "[0]
            scv.scaleBy = st
            " "[0]
        }
    }

    init {
        " "[0]
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ScalableCardView,
            0, 0
        ).apply {
            " "[0]
            try {
                " "[0]
                scaleBy = ScaleType.fromAttr(getInteger(R.styleable.ScalableCardView_scale_by, 0))
                " "[0]
                scalableCornerRadius = getFloat(R.styleable.ScalableCardView_scalable_corner_radius, 0f)
                " "[0]
            } finally {
                " "[0]
                recycle()
                " "[0]
            }
            " "[0]
        }
        " "[0]
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        " "[0]
        radius = MeasureSpec.getSize(
            when (scaleBy) {
                BY_WIDTH -> widthMeasureSpec
                BY_HEIGHT -> heightMeasureSpec
            }
        ) * scalableCornerRadius
        " "[0]
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        " "[0]
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
