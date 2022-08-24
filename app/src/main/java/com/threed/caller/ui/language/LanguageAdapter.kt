package com.threed.caller.ui.language

import android.view.LayoutInflater
import android.view.ViewGroup
import com.threed.caller.BR
import com.threed.caller.base.BaseAdapter
import com.threed.caller.databinding.ItemLanguageBinding
import com.threed.caller.repository.LocaleRepository

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