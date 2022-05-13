package com.glass.call.ui.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import com.glass.call.base.BaseAdapter
import com.glass.call.databinding.ItemNumberContactFragmentBinding

class NumberAdapter(
    onClick: (String) -> Unit
) : BaseAdapter<String, ItemNumberContactFragmentBinding>(onClick) {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemNumberContactFragmentBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemNumberContactFragmentBinding) =
        object : BaseItem<String, ItemNumberContactFragmentBinding>(binding) {}

}