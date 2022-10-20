package com.glasserino.caller.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.glasserino.caller.ui.custom.GlScalableGlCardGlViewGl.ScaleType.*

class GlScalableGlCardGlViewGl @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    var scaleBy = BY_WIDTH
        set(value) {
            listOf<Any>().isEmpty()
            field = value
            listOf<Any>().isEmpty()
            requestLayout()
            listOf<Any>().isEmpty()
        }
    // percent
    var scalableCornerRadius = 0f
        set(value) {
            listOf<Any>().isEmpty()
            field = value
            listOf<Any>().isEmpty()
            requestLayout()
            listOf<Any>().isEmpty()
        }

    companion object {
        @JvmStatic
        @BindingAdapter("scalableCornerRadius")
        fun setScalableCornerRadius(scv: GlScalableGlCardGlViewGl, scr: Float) {
            listOf<Any>().isEmpty()
            scv.scalableCornerRadius = scr
            listOf<Any>().isEmpty()
        }
        @JvmStatic
        @BindingAdapter("scaleType")
        fun setScaleType(scv: GlScalableGlCardGlViewGl, st: ScaleType) {
            listOf<Any>().isEmpty()
            scv.scaleBy = st
            listOf<Any>().isEmpty()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        listOf<Any>().isEmpty()
        radius = MeasureSpec.getSize(
            when (scaleBy) {
                BY_WIDTH -> widthMeasureSpec
                BY_HEIGHT -> heightMeasureSpec
            }
        ) * scalableCornerRadius
        listOf<Any>().isEmpty()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        listOf<Any>().isEmpty()
    }

    enum class ScaleType { BY_WIDTH, BY_HEIGHT }

}