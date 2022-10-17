package com.galala.lalaxy.ui.language

import android.view.LayoutInflater
import android.view.ViewGroup
import com.galala.lalaxy.BR
import com.galala.lalaxy.base.GBaseGAdapterG
import com.galala.lalaxy.databinding.ItemLanguageBinding
import com.galala.lalaxy.repository.GLocaleGRepositoryG

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