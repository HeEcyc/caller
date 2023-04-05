package com.paxi.cst.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.paxi.cst.base.BaseAdapter
import com.paxi.cst.databinding.ItemThemeVpBinding
import com.paxi.cst.model.theme.NewTheme
import com.paxi.cst.model.theme.Theme

class ThemeAdapterVP(
    private val onItemClick: (Theme) -> Unit = {}
) : BaseAdapter<Theme, ItemThemeVpBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemThemeVpBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemThemeVpBinding) =
        object : BaseItem<Theme, ItemThemeVpBinding>(binding) {
            override fun bind(t: Theme) {
                binding.root.setOnClickListener { onItemClick(t) }
                if (t !is NewTheme)
                    Glide.with(itemView.context.applicationContext).load(t.previewFile).centerCrop().into(binding.preview)
            }
        }

}