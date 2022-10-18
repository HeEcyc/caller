package com.fantasia.telecaller.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import com.fantasia.telecaller.base.FBaseFAdapterF
import com.fantasia.telecaller.databinding.ItemNumberSelectInterlocutorBinding

class FNumberFAdapterF(
    onClick: (NumberViewModel) -> Unit
) : FBaseFAdapterF<FNumberFAdapterF.NumberViewModel, ItemNumberSelectInterlocutorBinding>(onClick) {

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