package com.paxi.cst.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter

class GradientTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var colors = arrayOf(
        Color.parseColor("#12E0FF"),
        Color.parseColor("#66B0F0")
    ).toIntArray()
        set(value) {
            field = value
            requestLayout()
        }

    companion object {
        @JvmStatic
        @BindingAdapter("colors")
        fun GradientTextView.setGradientColors(colors: String) {
            this.colors = colors.split(' ').map { Color.parseColor(it) }.toIntArray()
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        doOnLayout(changed)
    }

    private fun doOnLayout(changed: Boolean) {
//        if (changed) {
            paint.shader = LinearGradient(
                0f, height.toFloat(), width.toFloat(), 0f,
                colors,
                null,
                Shader.TileMode.CLAMP
            )
//        }
    }

}
