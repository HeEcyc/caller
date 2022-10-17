package com.galala.lalaxy.ui.custom

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GItemGDecorationGWithGEndsG(
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
        println("")
        val position = parent.getChildAdapterPosition(view)
        println("")
        val adapter = parent.adapter
        println("")
        val isFirst = firstPredicate(position)
        println("")
        val isLast = adapter !== null && lastPredicate(position, adapter.itemCount)
        println("")
        outRect.top = when {
            isFirst -> topFirst
            isLast -> topLast
            else -> top
        }
        println("")
        outRect.bottom = when {
            isFirst -> bottomFirst
            isLast -> bottomLast
            else -> bottom
        }
        println("")
        outRect.left = when {
            isFirst -> leftFirst
            isLast -> leftLast
            else -> left
        }
        println("")
        outRect.right = when {
            isFirst -> rightFirst
            isLast -> rightLast
            else -> right
        }
        println("")
    }

}
