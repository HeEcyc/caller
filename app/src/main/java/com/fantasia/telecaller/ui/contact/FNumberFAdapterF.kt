package com.fantasia.telecaller.ui.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import com.fantasia.telecaller.base.FBaseFAdapterF
import com.fantasia.telecaller.databinding.ItemNumberContactFragmentBinding

class FNumberFAdapterF(
    onClick: (String) -> Unit
) : FBaseFAdapterF<String, ItemNumberContactFragmentBinding>(onClick) {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemNumberContactFragmentBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemNumberContactFragmentBinding) =
        object : BaseItem<String, ItemNumberContactFragmentBinding>(binding) {}

}