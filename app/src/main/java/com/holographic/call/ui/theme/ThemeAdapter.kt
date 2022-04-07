package com.holographic.call.ui.theme

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.holographic.call.base.BaseAdapter
import com.holographic.call.databinding.ItemThemeBinding
import com.holographic.call.model.theme.NewTheme
import com.holographic.call.model.theme.Theme

class ThemeAdapter(
    onItemClick: (Theme) -> Unit
) : BaseAdapter<Theme, ItemThemeBinding>(onItemClick) {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ): ItemThemeBinding = ItemThemeBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemThemeBinding): BaseItem<Theme, ItemThemeBinding> =
        object : BaseItem<Theme, ItemThemeBinding>(binding) {

            override fun bind(t: Theme) {
                if (t == NewTheme) return
                Glide.with(itemView.context.applicationContext).load(t.previewFile).centerCrop().into(binding.preview)
            }

        }

}
