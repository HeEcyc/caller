package com.yee.zer.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import com.bumptech.glide.Glide
import com.yee.zer.base.BaseAdapter
import com.yee.zer.databinding.ItemThemeHomeBinding
import com.yee.zer.model.theme.NewTheme
import com.yee.zer.model.theme.Theme

class ThemeAdapterHome(
    private val onItemClick: (Theme) -> Unit = {}
) : BaseAdapter<ThemeAdapterHome.Item, ItemThemeHomeBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemThemeHomeBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemThemeHomeBinding) =
        object : BaseItem<Item, ItemThemeHomeBinding>(binding) {
            override fun bind(t: Item) {
                binding.root.setOnClickListener { onItemClick(t.theme) }
                if (t.theme !is NewTheme) {
                    Glide.with(itemView.context.applicationContext).load(t.theme.previewFile).centerCrop().into(binding.preview1)
                    Glide.with(itemView.context.applicationContext).load(t.theme.previewFile).centerCrop().into(binding.preview2)
                }
            }
        }

    class Item(val theme: Theme, isSelected: Boolean = false) {
        val isSelected = ObservableBoolean(isSelected)
    }

}