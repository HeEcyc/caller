package com.megaaa.caaall.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.megaaa.caaall.BR

abstract class BaseAdapter<T, V : ViewDataBinding>(private val onItemClick: ((T) -> Unit)? = null) :
    RecyclerView.Adapter<BaseAdapter.BaseItem<T, out ViewDataBinding>>() {

    protected var items: MutableList<T> = mutableListOf()

    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): BaseItem<T, V> =
        createHolder(getViewBinding(LayoutInflater.from(viewGroup.context), viewGroup, i))

    abstract fun getViewBinding(inflater: LayoutInflater, viewGroup: ViewGroup, item: Int): V

    fun clearItems() {
        if (items.size > 0) {
            items.clear()
            notifyDataSetChanged()
        }
    }

    open fun addItem(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    open fun addItem(item: T, index: Int) {
        items.add(index, item)
        notifyItemInserted(index)
    }

    open fun reloadData(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun getData() = items

    open fun updateItem(pos: Int) {
        if (pos == -1) return
        notifyItemChanged(pos, Unit)
    }

    fun removeItem(pos: Int) {
        if (pos == -1) return
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: BaseItem<T, out ViewDataBinding>, position: Int) {
        items[position].let { item ->
            holder.setVariable(item)
            holder.binding.root.setOnClickListener { onItemClick?.invoke(item) }
            holder.bind(item)
        }
    }

    abstract fun createHolder(binding: V): BaseItem<T, V>

    abstract class BaseItem<T, V : ViewDataBinding>(val binding: V) :
        RecyclerView.ViewHolder(binding.root) {

        open fun setVariable(t: T) {
            binding.setVariable(BR.item, t)
            binding.executePendingBindings()
        }

        open fun bind(t: T) {

        }

    }

}
