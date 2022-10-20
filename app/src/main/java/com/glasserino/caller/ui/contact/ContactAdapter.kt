package com.glasserino.caller.ui.contact

import android.view.LayoutInflater
import android.view.ViewGroup
import com.glasserino.caller.base.GlBaseGlAdapterGl
import com.glasserino.caller.databinding.ItemNumberContactFragmentBinding

class GlNumberGlAdapterGl(
    onClick: (String) -> Unit
) : GlBaseGlAdapterGl<String, ItemNumberContactFragmentBinding>(onClick) {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemNumberContactFragmentBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemNumberContactFragmentBinding) =
        object : BaseItem<String, ItemNumberContactFragmentBinding>(binding) {}

}