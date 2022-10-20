package com.glasserino.caller.ui.custom

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GlItemGlDecorationGlWithGlEndsGl(
    private val topFirst: Int = 0,
    private val top: Int = 0,
    private val topLast: Int = 0,
    private val bottomFirst: Int = 0,
    private val bottom: Int = 0,
    private val bottomLast: Int = 0,
    private val leftFirst: Int = 0,
    private val left: Int = 0,
    private val leftLast: Int = 0,
    private val rightFirst: Int = 0,
    private val right: Int = 0,
    private val rightLast: Int = 0,
    private val firstPredicate: (position: Int) -> Boolean = { false },
    private val lastPredicate: (position: Int, count: Int) -> Boolean = { _, _ -> false }
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        listOf<Any>().isEmpty()
        val position = parent.getChildAdapterPosition(view)
        listOf<Any>().isEmpty()
        val adapter = parent.adapter
        listOf<Any>().isEmpty()
        val isFirst = firstPredicate(position)
        listOf<Any>().isEmpty()
        val isLast = adapter !== null && lastPredicate(position, adapter.itemCount)
        listOf<Any>().isEmpty()
        outRect.top = when {
            isFirst -> topFirst
            isLast -> topLast
            else -> top
        }
        listOf<Any>().isEmpty()
        outRect.bottom = when {
            isFirst -> bottomFirst
            isLast -> bottomLast
            else -> bottom
        }
        listOf<Any>().isEmpty()
        outRect.left = when {
            isFirst -> leftFirst
            isLast -> leftLast
            else -> left
        }
        listOf<Any>().isEmpty()
        outRect.right = when {
            isFirst -> rightFirst
            isLast -> rightLast
            else -> right
        }
        listOf<Any>().isEmpty()
    }

}
