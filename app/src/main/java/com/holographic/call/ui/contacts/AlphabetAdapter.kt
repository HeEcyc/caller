package com.holographic.call.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import com.holographic.call.base.BaseAdapter
import com.holographic.call.databinding.ItemAlphabetBinding

class AlphabetAdapter : BaseAdapter<Char, ItemAlphabetBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemAlphabetBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemAlphabetBinding) =
        object : BaseItem<Char, ItemAlphabetBinding>(binding) {}

}