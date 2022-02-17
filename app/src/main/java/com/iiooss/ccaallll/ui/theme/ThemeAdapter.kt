package com.iiooss.ccaallll.ui.theme

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.iiooss.ccaallll.base.BaseAdapter
import com.iiooss.ccaallll.databinding.ItemThemeBinding
import com.iiooss.ccaallll.model.theme.NewTheme
import com.iiooss.ccaallll.model.theme.Theme

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
