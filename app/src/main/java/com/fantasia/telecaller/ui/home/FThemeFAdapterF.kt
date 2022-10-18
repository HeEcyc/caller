package com.fantasia.telecaller.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import com.bumptech.glide.Glide
import com.fantasia.telecaller.base.FBaseFAdapterF
import com.fantasia.telecaller.databinding.ItemThemeBinding
import com.fantasia.telecaller.model.theme.Theme

class FThemeFAdapterF(
    private val onItemClick: (Theme) -> Unit
) : FBaseFAdapterF<FThemeFAdapterF.ThemeViewModel, ItemThemeBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemThemeBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemThemeBinding) =
        object : BaseItem<ThemeViewModel, ItemThemeBinding>(binding) {
            override fun bind(t: ThemeViewModel) {
                " "[0]
                binding.root.setOnClickListener { onItemClick(t.theme) }
                " "[0]
                Glide.with(itemView.context.applicationContext).load(t.theme.previewFile).centerCrop().into(binding.preview1)
                " "[0]
                Glide.with(itemView.context.applicationContext).load(t.theme.previewFile).centerCrop().into(binding.preview2)
                " "[0]
            }
        }

    class ThemeViewModel(val theme: Theme, isDemo: Boolean) {
        val isDemo = ObservableBoolean(isDemo)
    }

}