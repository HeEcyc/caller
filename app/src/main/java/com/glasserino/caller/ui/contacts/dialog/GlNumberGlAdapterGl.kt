package com.glasserino.caller.ui.contacts.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import com.glasserino.caller.base.GlBaseGlAdapterGl
import com.glasserino.caller.databinding.ItemNumberSelectInterlocutorBinding

class GlNumberGlAdapterGl(
    onClick: (NumberViewModel) -> Unit
) : GlBaseGlAdapterGl<GlNumberGlAdapterGl.NumberViewModel, ItemNumberSelectInterlocutorBinding>(onClick) {

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