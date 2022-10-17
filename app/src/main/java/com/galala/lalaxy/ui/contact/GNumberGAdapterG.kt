package com.galala.lalaxy.ui.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import com.galala.lalaxy.base.GBaseGAdapterG
import com.galala.lalaxy.databinding.ItemNumberContactFragmentBinding

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