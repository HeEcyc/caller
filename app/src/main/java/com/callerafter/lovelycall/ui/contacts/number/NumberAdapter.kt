package com.callerafter.lovelycall.ui.contacts.number

import android.view.LayoutInflater
import android.view.ViewGroup
import com.callerafter.lovelycall.base.BaseAdapter
import com.callerafter.lovelycall.databinding.ItemNumberSelectInterlocutorBinding

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