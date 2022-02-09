package com.megaaa.caaall.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.megaaa.caaall.base.BaseAdapter
import com.megaaa.caaall.databinding.ItemThemeBinding
import com.megaaa.caaall.model.theme.NewTheme
import com.megaaa.caaall.model.theme.Theme

class ThemeAdapter(
    onItemClick: (Theme) -> Unit,
    private val hasDemoItem: Boolean = false
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

            override fun setVariable(t: Theme) {
                if (hasDemoItem) binding.isDemo = layoutPosition == 0
                super.setVariable(t)
            }

        }

}