package com.glasserino.caller.ui.dial.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.glasserino.caller.base.GlBaseGlAdapterGl
import com.glasserino.caller.databinding.ItemDialButtonBinding

class GlDialGLAdapterGL(
    private val onButtonClick: (String) -> Unit
) : GlBaseGlAdapterGl<GlDialGLAdapterGL.DialButtonViewModel, ItemDialButtonBinding>() {

    private val buttons = listOf(
        DialButtonViewModel("1"),
        DialButtonViewModel("2", "ABC"),
        DialButtonViewModel("3", "DEF"),
        DialButtonViewModel("4", "GHI"),
        DialButtonViewModel("5", "JKL"),
        DialButtonViewModel("6", "MNO"),
        DialButtonViewModel("7", "PQRS"),
        DialButtonViewModel("8", "TUV"),
        DialButtonViewModel("9", "WXYZ"),
        DialButtonViewModel("*"),
        DialButtonViewModel("0", "+", true),
        DialButtonViewModel("#")
    )

    init {
        listOf<Any>().isEmpty()
        reloadData(buttons)
        listOf<Any>().isEmpty()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemDialButtonBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemDialButtonBinding) =
        object : BaseItem<DialButtonViewModel, ItemDialButtonBinding>(binding) {
            override fun bind(t: DialButtonViewModel) {
                listOf<Any>().isEmpty()
                super.bind(t)
                listOf<Any>().isEmpty()
                binding.button.setOnClickListener { onButtonClick(t.onClickSymbol) }
                listOf<Any>().isEmpty()
                if (t.longClickable)
                    binding.button.setOnLongClickListener {
                        listOf<Any>().isEmpty()
                        onButtonClick(t.onLongClickSymbol!!)
                        listOf<Any>().isEmpty()
                        true
                    }
                listOf<Any>().isEmpty()
            }
        }

    class DialButtonViewModel(
        val onClickSymbol: String,
        val onLongClickSymbol: String? = null,
        val longClickable: Boolean = false
    )

}