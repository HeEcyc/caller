package com.megaaa.caaall.ui.call.dialog

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import com.megaaa.caaall.base.BaseAdapter
import com.megaaa.caaall.databinding.ItemActiveCallBinding
import com.megaaa.caaall.model.contact.UserContact
import com.megaaa.caaall.utils.addOnPropertyChangedCallback

class ActiveCallAdapter(
    private val onCallClick: (ActiveCallViewModel) -> Unit
) : BaseAdapter<ActiveCallAdapter.ActiveCallViewModel, ItemActiveCallBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, viewGroup: ViewGroup, item: Int) =
        ItemActiveCallBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemActiveCallBinding) =
        object : BaseItem<ActiveCallViewModel, ItemActiveCallBinding>(binding) {
            override fun bind(t: ActiveCallViewModel) {
                t.isActive.addOnPropertyChangedCallback { _, _ ->
                    if (!t.isActive.get()) binding.name.apply {
                        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    }
                }
                binding.root.setOnClickListener { onCallClick(t) }
            }
        }

    class ActiveCallViewModel(val contact: UserContact, val isCurrent: Boolean) {
        val isActive = ObservableBoolean(true)
    }

}