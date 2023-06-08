package com.fusiecal.ler.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import com.bumptech.glide.Glide
import com.fusiecal.ler.base.BaseAdapter
import com.fusiecal.ler.databinding.ItemThemeRvBinding
import com.fusiecal.ler.model.theme.NewTheme
import com.fusiecal.ler.model.theme.Theme

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