package com.vefercal.ler.ui.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vefercal.ler.base.BaseAdapter
import com.vefercal.ler.databinding.ItemNumberContactFragmentBinding

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