package com.fantasia.telecaller.ui.language

import android.view.LayoutInflater
import android.view.ViewGroup
import com.fantasia.telecaller.BR
import com.fantasia.telecaller.base.BaseAdapter
import com.fantasia.telecaller.databinding.ItemLanguageBinding
import com.fantasia.telecaller.repository.LocaleRepository

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