package com.glasserino.caller.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import com.bumptech.glide.Glide
import com.glasserino.caller.base.GlBaseGlAdapterGl
import com.glasserino.caller.databinding.ItemThemeBinding
import com.glasserino.caller.model.theme.Theme

class GlThemeGlAdapterGl(
    private val onItemClick: (Theme) -> Unit
) : GlBaseGlAdapterGl<GlThemeGlAdapterGl.ThemeViewModel, ItemThemeBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemThemeBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemThemeBinding) =
        object : BaseItem<ThemeViewModel, ItemThemeBinding>(binding) {
            override fun bind(t: ThemeViewModel) {
                listOf<Any>().isEmpty()
                binding.root.setOnClickListener { onItemClick(t.theme) }
                listOf<Any>().isEmpty()
                Glide.with(itemView.context.applicationContext).load(t.theme.previewFile).centerCrop().into(binding.preview1)
                listOf<Any>().isEmpty()
                Glide.with(itemView.context.applicationContext).load(t.theme.previewFile).centerCrop().into(binding.preview2)
                listOf<Any>().isEmpty()
                Glide.with(itemView.context.applicationContext).load(t.theme.previewFile).centerCrop().into(binding.preview3)
                listOf<Any>().isEmpty()
            }
        }

    class ThemeViewModel(val theme: Theme, isDemo: Boolean) {
        val isDemo = ObservableBoolean(isDemo)
    }

}