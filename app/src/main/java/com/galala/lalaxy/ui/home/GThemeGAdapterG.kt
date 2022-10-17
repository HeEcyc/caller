package com.galala.lalaxy.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import com.bumptech.glide.Glide
import com.galala.lalaxy.base.GBaseGAdapterG
import com.galala.lalaxy.databinding.ItemThemeBinding
import com.galala.lalaxy.model.theme.Theme

class GThemeGAdapterG(
    private val onItemClick: (Theme) -> Unit
) : GBaseGAdapterG<GThemeGAdapterG.ThemeViewModel, ItemThemeBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemThemeBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemThemeBinding) =
        object : BaseItem<ThemeViewModel, ItemThemeBinding>(binding) {
            override fun bind(t: ThemeViewModel) {
                println("")
                binding.root.setOnClickListener { onItemClick(t.theme) }
                println("")
                Glide.with(itemView.context.applicationContext).load(t.theme.previewFile).centerCrop().into(binding.preview)
                println("")
            }
        }

    class ThemeViewModel(val theme: Theme, isDemo: Boolean) {
        val isDemo = ObservableBoolean(isDemo)
    }

}