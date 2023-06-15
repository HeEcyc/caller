package com.docall.jocall.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import com.bumptech.glide.Glide
import com.docall.jocall.base.BaseAdapter
import com.docall.jocall.databinding.ItemThemeRvBinding
import com.docall.jocall.model.theme.NewTheme
import com.docall.jocall.model.theme.Theme

class ThemeAdapterRV(
    private val onItemClick: (Theme) -> Unit = {},
    private val onPreviewClick: (Theme) -> Unit = {}
) : BaseAdapter<ThemeAdapterRV.ThemeViewModel, ItemThemeRvBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemThemeRvBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemThemeRvBinding) =
        object : BaseItem<ThemeViewModel, ItemThemeRvBinding>(binding) {
            override fun bind(t: ThemeViewModel) {
                binding.button.setOnClickListener { onItemClick(t.theme) }
                binding.preview.setOnClickListener { onPreviewClick(t.theme) }
                if (t.theme !is NewTheme)
                    Glide.with(itemView.context.applicationContext).load(t.theme.previewFile).centerCrop().into(binding.preview)
            }
        }

    class ThemeViewModel(val theme: Theme, isSelected: Boolean) {
        val isSelected = ObservableBoolean(isSelected)
    }

}