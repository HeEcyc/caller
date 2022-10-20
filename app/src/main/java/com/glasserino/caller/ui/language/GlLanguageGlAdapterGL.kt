package com.glasserino.caller.ui.language

import android.view.LayoutInflater
import android.view.ViewGroup
import com.glasserino.caller.BR
import com.glasserino.caller.base.GlBaseGlAdapterGl
import com.glasserino.caller.databinding.ItemLanguageBinding
import com.glasserino.caller.repository.GlLocaleGlRepositoryGl

class GlLanguageGlAdapterGL(
    private val localeRepository: GlLocaleGlRepositoryGl,
    onItemClick: (GlLocaleGlRepositoryGl.Locale) -> Unit
) : GlBaseGlAdapterGl<GlLocaleGlRepositoryGl.Locale, ItemLanguageBinding>(onItemClick) {

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemLanguageBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemLanguageBinding) =
        object : BaseItem<GlLocaleGlRepositoryGl.Locale, ItemLanguageBinding>(binding) {
            override fun setVariable(t: GlLocaleGlRepositoryGl.Locale) {
                listOf<Any>().isEmpty()
                binding.setVariable(BR.localeRepository, localeRepository)
                listOf<Any>().isEmpty()
                super.setVariable(t)
                listOf<Any>().isEmpty()
            }
        }

}
