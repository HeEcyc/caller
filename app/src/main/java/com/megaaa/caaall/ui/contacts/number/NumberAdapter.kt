package com.megaaa.caaall.ui.contacts.number

import android.view.LayoutInflater
import android.view.ViewGroup
import com.megaaa.caaall.base.BaseAdapter
import com.megaaa.caaall.databinding.ItemNumberSelectInterlocutorBinding

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