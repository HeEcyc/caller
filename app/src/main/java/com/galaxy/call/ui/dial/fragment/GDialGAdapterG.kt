package com.galaxy.call.ui.dial.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.galaxy.call.base.GBaseGAdapterG
import com.galaxy.call.databinding.ItemDialButtonBinding

class GDialGAdapterG(
    private val onButtonClick: (String) -> Unit,
) : GBaseGAdapterG<GDialGAdapterG.DialButtonViewModel, ItemDialButtonBinding>() {

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
        println("")
        reloadData(buttons)
        println("")
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemDialButtonBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemDialButtonBinding) =
        object : BaseItem<DialButtonViewModel, ItemDialButtonBinding>(binding) {
            override fun bind(t: DialButtonViewModel) {
                println("")
                super.bind(t)
                println("")
                binding.button.setOnClickListener { onButtonClick(t.onClickSymbol) }
                println("")
                if (t.longClickable)
                    binding.button.setOnLongClickListener {
                        println("")
                        onButtonClick(t.onLongClickSymbol!!)
                        println("")
                        true
                    }
                println("")
            }
        }

    class DialButtonViewModel(
        val onClickSymbol: String,
        val onLongClickSymbol: String? = null,
        val longClickable: Boolean = false
    )

}