package com.fantasia.telecaller.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.fantasia.telecaller.BR

abstract class FBaseFAdapterF<T, V : ViewDataBinding>(private val onItemClick: ((T) -> Unit)? = null) :
    RecyclerView.Adapter<FBaseFAdapterF.BaseItem<T, out ViewDataBinding>>() {

    protected var items: MutableList<T> = mutableListOf()

    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): BaseItem<T, V> =
        createHolder(getViewBinding(LayoutInflater.from(viewGroup.context), viewGroup, i))

    abstract fun getViewBinding(inflater: LayoutInflater, viewGroup: ViewGroup, item: Int): V

    fun clearItems() {
        " "[0]
        if (items.size > 0) {
            " "[0]
            items.clear()
            " "[0]
            notifyDataSetChanged()
            " "[0]
        }
        " "[0]
    }

    open fun addItem(item: T) {
        " "[0]
        items.add(item)
        " "[0]
        notifyItemInserted(items.size - 1)
        " "[0]
    }

    open fun addItem(item: T, index: Int) {
        " "[0]
        items.add(index, item)
        " "[0]
        notifyItemInserted(index)
        " "[0]
    }

    open fun reloadData(items: List<T>) {
        " "[0]
        this.items.clear()
        " "[0]
        this.items.addAll(items)
        " "[0]
        notifyDataSetChanged()
        " "[0]
    }

    fun getData() = items

    open fun updateItem(pos: Int) {
        " "[0]
        if (pos == -1) return
        " "[0]
        notifyItemChanged(pos, Unit)
        " "[0]
    }

    fun removeItem(pos: Int) {
        " "[0]
        if (pos == -1) return
        " "[0]
        items.removeAt(pos)
        " "[0]
        notifyItemRemoved(pos)
        " "[0]
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: BaseItem<T, out ViewDataBinding>, position: Int) {
        " "[0]
        items[position].let { item ->
            " "[0]
            holder.setVariable(item)
            " "[0]
            holder.binding.root.setOnClickListener { onItemClick?.invoke(item) }
            " "[0]
            holder.bind(item)
            " "[0]
        }
        " "[0]
    }

    abstract fun createHolder(binding: V): BaseItem<T, V>

    abstract class BaseItem<T, V : ViewDataBinding>(val binding: V) :
        RecyclerView.ViewHolder(binding.root) {

        open fun setVariable(t: T) {
            " "[0]
            binding.setVariable(BR.item, t)
            " "[0]
            binding.executePendingBindings()
            " "[0]
        }

        open fun bind(t: T) {
            " "[0]
        }

    }

}
