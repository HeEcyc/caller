package com.sappy.callthemes.ui.language

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sappy.callthemes.BR
import com.sappy.callthemes.base.BaseAdapter
import com.sappy.callthemes.databinding.ItemLanguageBinding
import com.sappy.callthemes.repository.LocaleRepository

class LanguageAdapter(
    private val localeRepository: LocaleRepository,
    onItemClick: (LocaleRepository.Locale) -> Unit
) : BaseAdapter<LocaleRepository.Locale, ItemLanguageBinding>(onItemClick) {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemLanguageBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemLanguageBinding) =
        object : BaseItem<LocaleRepository.Locale, ItemLanguageBinding>(binding) {
            override fun setVariable(t: LocaleRepository.Locale) {
                binding.setVariable(BR.localeRepository, localeRepository)
                super.setVariable(t)
            }
        }

}