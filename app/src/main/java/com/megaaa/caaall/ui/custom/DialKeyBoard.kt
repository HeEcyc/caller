package com.megaaa.caaall.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.megaaa.caaall.R
import com.megaaa.caaall.base.BaseAdapter
import com.megaaa.caaall.databinding.ItemDialButtonBinding
import com.megaaa.caaall.databinding.ViewDialKeyboardBinding

class DialKeyBoard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var onButtonClickListener: ((symbol: String) -> Unit)? = null

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

    val binding: ViewDialKeyboardBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_dial_keyboard,
        this,
        true
    )

    init {
        binding.buttonBackspace.setOnClickListener {
            binding.textView.apply { text = text?.dropLast(1) }
        }
        binding.recyclerView.adapter = DialButtonAdapter().apply { reloadData(buttons) }
    }

    @SuppressLint("SetTextI18n")
    private fun onButtonClick(symbol: String) {
        binding.textView.apply { text = (text?.toString() ?: "") + symbol }
        onButtonClickListener?.invoke(symbol)
    }

    class DialButtonViewModel(
        val onClickSymbol: String,
        val onLongClickSymbol: String? = null,
        val longClickable: Boolean = false
    )

    private inner class DialButtonAdapter : BaseAdapter<DialButtonViewModel, ItemDialButtonBinding>() {

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

    }

}