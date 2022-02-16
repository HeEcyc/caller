package com.iiooss.ccaallll.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import com.iiooss.ccaallll.base.BaseAdapter
import com.iiooss.ccaallll.databinding.ItemAlphabetBinding

class AlphabetAdapter : BaseAdapter<Char, ItemAlphabetBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemAlphabetBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemAlphabetBinding) =
        object : BaseItem<Char, ItemAlphabetBinding>(binding) {}

}