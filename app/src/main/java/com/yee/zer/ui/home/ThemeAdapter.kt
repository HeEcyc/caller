package com.yee.zer.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.yee.zer.base.BaseAdapter
import com.yee.zer.databinding.ItemThemeBinding
import com.yee.zer.model.theme.NewTheme
import com.yee.zer.model.theme.Theme

class ThemeAdapter(
    private val onItemClick: (Theme) -> Unit = {}
) : BaseAdapter<Theme, ItemThemeBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemThemeBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemThemeBinding) =
        object : BaseItem<Theme, ItemThemeBinding>(binding) {
            override fun bind(t: Theme) {
                binding.root.setOnClickListener { onItemClick(t) }
                if (t !is NewTheme)
                    Glide.with(itemView.context.applicationContext).load(t.previewFile).centerCrop().into(binding.preview)
            }
        }

}