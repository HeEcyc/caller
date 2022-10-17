package com.galaxy.call.ui.language

import android.view.LayoutInflater
import android.view.ViewGroup
import com.galaxy.call.BR
import com.galaxy.call.base.GBaseGAdapterG
import com.galaxy.call.databinding.ItemLanguageBinding
import com.galaxy.call.repository.GLocaleGRepositoryG

class GLanguageGAdapterG(
    private val localeRepository: GLocaleGRepositoryG,
    onItemClick: (GLocaleGRepositoryG.Locale) -> Unit
) : GBaseGAdapterG<GLocaleGRepositoryG.Locale, ItemLanguageBinding>(onItemClick) {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemLanguageBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemLanguageBinding) =
        object : BaseItem<GLocaleGRepositoryG.Locale, ItemLanguageBinding>(binding) {
            override fun setVariable(t: GLocaleGRepositoryG.Locale) {
                println("")
                binding.setVariable(BR.localeRepository, localeRepository)
                println("")
                super.setVariable(t)
                println("")
            }
        }

}