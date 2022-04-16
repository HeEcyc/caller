package com.maxios.maxcall.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import com.maxios.maxcall.base.BaseAdapter
import com.maxios.maxcall.databinding.ItemNumberSelectInterlocutorBinding

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