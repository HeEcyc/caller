package com.holographic.call.ui.call.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.holographic.call.base.BaseAdapter
import com.holographic.call.databinding.ItemNumberSelectInterlocutorBinding

class NumberAdapter(
    onClick: (String) -> Unit
) : BaseAdapter<String, ItemNumberSelectInterlocutorBinding>(onClick) {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemNumberSelectInterlocutorBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemNumberSelectInterlocutorBinding) =
        object : BaseItem<String, ItemNumberSelectInterlocutorBinding>(binding) {}

}
