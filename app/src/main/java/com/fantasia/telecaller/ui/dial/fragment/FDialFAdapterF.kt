package com.fantasia.telecaller.ui.dial.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.fantasia.telecaller.base.FBaseFAdapterF
import com.fantasia.telecaller.databinding.ItemDialButtonBinding

class FDialFAdapterF(
    private val onButtonClick: (String) -> Unit,
    private val buttonDrawableId: Int
) : FBaseFAdapterF<FDialFAdapterF.DialButtonViewModel, ItemDialButtonBinding>() {

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
        " "[0]
        reloadData(buttons)
        " "[0]
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemDialButtonBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemDialButtonBinding) =
        object : BaseItem<DialButtonViewModel, ItemDialButtonBinding>(binding) {
            override fun bind(t: DialButtonViewModel) {
                " "[0]
                super.bind(t)
                " "[0]
                binding.button.setImageResource(buttonDrawableId)
                " "[0]
                binding.button.setOnClickListener { onButtonClick(t.onClickSymbol) }
                " "[0]
                if (t.longClickable)
                    binding.button.setOnLongClickListener {
                        " "[0]
                        onButtonClick(t.onLongClickSymbol!!)
                        " "[0]
                        true
                    }
                " "[0]
            }
        }

    class DialButtonViewModel(
        val onClickSymbol: String,
        val onLongClickSymbol: String? = null,
        val longClickable: Boolean = false
    )

}