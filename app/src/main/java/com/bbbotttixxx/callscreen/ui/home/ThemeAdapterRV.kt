package com.bbbotttixxx.callscreen.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bbbotttixxx.callscreen.base.BaseAdapter
import com.bbbotttixxx.callscreen.databinding.ItemThemeRvBinding
import com.bbbotttixxx.callscreen.model.theme.NewTheme
import com.bbbotttixxx.callscreen.model.theme.Theme

class ThemeAdapterRV(
    private val onItemClick: (Theme) -> Unit = {}
) : BaseAdapter<Theme, ItemThemeRvBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemThemeRvBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemThemeRvBinding) =
        object : BaseItem<Theme, ItemThemeRvBinding>(binding) {
            override fun bind(t: Theme) {
                binding.root.setOnClickListener { onItemClick(t) }
                if (t !is NewTheme)
                    Glide.with(itemView.context.applicationContext).load(t.previewFile).centerCrop().into(binding.preview)
            }
        }

}