package com.iiooss.ccaallll.ui.dial.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.iiooss.ccaallll.base.BaseAdapter
import com.iiooss.ccaallll.databinding.ItemDialButtonBinding

class DialAdapter(
    private val onButtonClick: (String) -> Unit
) : BaseAdapter<DialAdapter.DialButtonViewModel, ItemDialButtonBinding>() {

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
        reloadData(buttons)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup,
        item: Int
    ) = ItemDialButtonBinding.inflate(inflater, viewGroup, false)

    override fun createHolder(binding: ItemDialButtonBinding) =
        object : BaseItem<DialButtonViewModel, ItemDialButtonBinding>(binding) {
            override fun bind(t: DialButtonViewModel) {
                super.bind(t)
                binding.root.setOnClickListener { onButtonClick(t.onClickSymbol) }
                if (t.longClickable)
                    binding.root.setOnLongClickListener {
                        onButtonClick(t.onLongClickSymbol!!)
                        true
                    }
            }
        }

    class DialButtonViewModel(
        val onClickSymbol: String,
        val onLongClickSymbol: String? = null,
        val longClickable: Boolean = false
    )

}
