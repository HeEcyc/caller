package com.megaaa.caaall.ui.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.megaaa.caaall.base.BaseAdapter
import com.megaaa.caaall.databinding.ItemNumberContactBinding
import com.megaaa.caaall.databinding.ItemNumberContactDividerBinding
import java.lang.IllegalStateException

class NumberAdapter(
    private val onClick: (String) -> Unit
) : BaseAdapter<String?, ViewDataBinding>() {

    companion object {
        private const val IVT_ITEM = 0
        private const val IVT_DIVIDER = 1

        fun getFormattedItemList(items: List<String>): List<String?> {
            if (items.isEmpty()) return listOf()
            val lastIndex = items.size - 1
            return items.flatMapIndexed { index: Int, s: String ->
                if (index == lastIndex) listOf(s) else listOf(s, null)
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = when (item) {
        IVT_ITEM -> ItemNumberContactBinding.inflate(inflater, viewGroup, false)
        IVT_DIVIDER -> ItemNumberContactDividerBinding.inflate(inflater, viewGroup, false)
        else -> throw IllegalStateException("impossible")
    }

    override fun getItemViewType(position: Int) = position % 2

    override fun onBindViewHolder(holder: BaseItem<String?, out ViewDataBinding>, position: Int) {
        items[position]?.let { item ->
            holder.setVariable(item)
            holder.binding.root.setOnClickListener { onClick(item) }
            holder.bind(item)
        }
    }

    override fun createHolder(binding: ViewDataBinding) =
        object : BaseItem<String?, ViewDataBinding>(binding) {}

}