package com.fantasia.telecaller.ui.language

import android.view.LayoutInflater
import android.view.ViewGroup
import com.fantasia.telecaller.BR
import com.fantasia.telecaller.base.FBaseFAdapterF
import com.fantasia.telecaller.databinding.ItemLanguageBinding
import com.fantasia.telecaller.repository.FLocaleFRepositoryF

class FLanguageFAdapterF(
    private val localeRepository: FLocaleFRepositoryF,
    onItemClick: (FLocaleFRepositoryF.Locale) -> Unit
) : FBaseFAdapterF<FLocaleFRepositoryF.Locale, ItemLanguageBinding>(onItemClick) {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemLanguageBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemLanguageBinding) =
        object : BaseItem<FLocaleFRepositoryF.Locale, ItemLanguageBinding>(binding) {
            override fun setVariable(t: FLocaleFRepositoryF.Locale) {
                " "[0]
                binding.setVariable(BR.localeRepository, localeRepository)
                " "[0]
                super.setVariable(t)
                " "[0]
            }
        }

}