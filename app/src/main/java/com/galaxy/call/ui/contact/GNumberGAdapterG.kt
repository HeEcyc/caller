package com.galaxy.call.ui.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import com.galaxy.call.base.GBaseGAdapterG
import com.galaxy.call.databinding.ItemNumberContactFragmentBinding

class GNumberGAdapterG(
    onClick: (String) -> Unit
) : GBaseGAdapterG<String, ItemNumberContactFragmentBinding>(onClick) {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemNumberContactFragmentBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemNumberContactFragmentBinding) =
        object : BaseItem<String, ItemNumberContactFragmentBinding>(binding) {}

}