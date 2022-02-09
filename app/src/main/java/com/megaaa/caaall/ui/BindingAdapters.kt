package com.megaaa.caaall.ui

import android.graphics.Typeface
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.megaaa.caaall.ui.custom.ItemDecorationWithEnds

@BindingAdapter("adapter")
fun setRVAdapter(rv: RecyclerView, rva: RecyclerView.Adapter<*>) {
    rv.adapter = rva
}

@BindingAdapter("textStyle")
fun setTextStyle(tv: TextView, style: Int) {
    tv.typeface = Typeface.create(tv.typeface, style)
}

@BindingAdapter("contactsFragment_isLinearLayoutManager")
fun setLayoutManager(rv: RecyclerView, isLLM: Boolean) {
    rv.layoutManager =
        if (isLLM)
            LinearLayoutManager(rv.context, LinearLayoutManager.VERTICAL, false)
        else
            GridLayoutManager(rv.context, 3)
    rv.post {
        for (i in rv.itemDecorationCount - 1 downTo 0) rv.removeItemDecorationAt(i)
        if (isLLM)
            ItemDecorationWithEnds(
                topFirst = rv.width / 360 * 70,
                bottomLast = rv.width / 360 * 245,
                firstPredicate = ::linearIsFirst,
                lastPredicate = ::linearIsLast
            ).let(rv::addItemDecoration)
        else {
            ItemDecorationWithEnds(
                topFirst = rv.width / 360 * 75,
                bottomLast = rv.width / 360 * 245,
                firstPredicate = ::gridIsFirstVertical,
                lastPredicate = ::gridIsLastVertical
            ).let(rv::addItemDecoration)
//            val isLtr = rv.resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_LTR
//            val gridIsFirstHorizontal: (Int) -> Boolean =
//                { position -> position % 3 == if (isLtr) 0 else 2 }
//            val gridIsLastHorizontal: (Int, Int) -> Boolean =
//                { position, _ -> position % 3 == if (isLtr) 2 else 0 }
//            val offset = (rv.width / 360 * 17.42f).toInt()
//            val defaultOffset = (rv.width / 360 * 5.14f).toInt()
//            ItemDecorationWithEnds(
//                leftFirst = if (isLtr) offset else defaultOffset,
//                left = defaultOffset,
//                leftLast = if (isLtr) defaultOffset else offset,
//                rightFirst = if (isLtr) defaultOffset else offset,
//                right = defaultOffset,
//                rightLast = if (isLtr) offset else defaultOffset,
//                firstPredicate = gridIsFirstHorizontal,
//                lastPredicate = gridIsLastHorizontal
//            )//.let(rv::addItemDecoration)
        }
    }
}

private fun linearIsFirst(position: Int) = position == 0

private fun linearIsLast(position: Int, count: Int) = position == count - 1

private fun gridIsFirstVertical(position: Int) = position in 0..2

private fun gridIsLastVertical(position: Int, count: Int) =
    position == count - 1 ||
    (count % 3 in listOf(0, 2) && position == count - 2) ||
    (count % 3 == 0 && position == count - 3)

@BindingAdapter("icon")
fun setIcon(iv: AppCompatImageView, r: Int) {
    iv.setImageResource(r)
}