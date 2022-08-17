package com.fancy.call.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import com.bumptech.glide.Glide
import com.fancy.call.base.BaseAdapter
import com.fancy.call.databinding.ItemThemeBinding
import com.fancy.call.model.theme.Theme

class ThemeAdapter(
    private val onItemClick: (Theme) -> Unit
) : BaseAdapter<ThemeAdapter.ThemeViewModel, ItemThemeBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemThemeBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemThemeBinding) =
        object : BaseItem<ThemeViewModel, ItemThemeBinding>(binding) {
            override fun bind(t: ThemeViewModel) {
                binding.root.setOnClickListener { onItemClick(t.theme) }
                Glide.with(itemView.context.applicationContext).load(t.theme.previewFile).centerCrop().into(binding.preview)
            }
        }

    class ThemeViewModel(val theme: Theme, isDemo: Boolean) {
        val isDemo = ObservableBoolean(isDemo)
    }

}