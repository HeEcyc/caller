package com.callerafter.lovelycall.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.callerafter.lovelycall.base.BaseAdapter
import com.callerafter.lovelycall.databinding.ItemThemeBinding
import com.callerafter.lovelycall.model.theme.NewTheme
import com.callerafter.lovelycall.model.theme.Theme

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