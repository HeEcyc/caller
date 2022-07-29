package com.galaxy.call.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import com.galaxy.call.base.BaseAdapter
import com.galaxy.call.databinding.ItemNumberSelectInterlocutorBinding

class NumberAdapter(
    onClick: (NumberViewModel) -> Unit
) : BaseAdapter<NumberAdapter.NumberViewModel, ItemNumberSelectInterlocutorBinding>(onClick) {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemNumberSelectInterlocutorBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemNumberSelectInterlocutorBinding) =
        object : BaseItem<NumberViewModel, ItemNumberSelectInterlocutorBinding>(binding) {}


    class NumberViewModel(val number: String, isSelected: Boolean) {
        val isSelected = ObservableBoolean(isSelected)
    }

}