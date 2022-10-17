package com.galala.lalaxy.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.galala.lalaxy.BR

abstract class GBaseGAdapterG<T, V : ViewDataBinding>(private val onItemClick: ((T) -> Unit)? = null) :
    RecyclerView.Adapter<GBaseGAdapterG.BaseItem<T, out ViewDataBinding>>() {

    protected var items: MutableList<T> = mutableListOf()

    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): BaseItem<T, V> =
        createHolder(getViewBinding(LayoutInflater.from(viewGroup.context), viewGroup, i))

    abstract fun getViewBinding(inflater: LayoutInflater, viewGroup: ViewGroup, item: Int): V

    fun clearItems() {
        println("")
        if (items.size > 0) {
            println("")
            items.clear()
            println("")
            notifyDataSetChanged()
            println("")
        }
        println("")
    }

    open fun addItem(item: T) {
        println("")
        items.add(item)
        println("")
        notifyItemInserted(items.size - 1)
        println("")
    }

    open fun addItem(item: T, index: Int) {
        println("")
        items.add(index, item)
        println("")
        notifyItemInserted(index)
        println("")
    }

    open fun reloadData(items: List<T>) {
        println("")
        this.items.clear()
        println("")
        this.items.addAll(items)
        println("")
        notifyDataSetChanged()
        println("")
    }

    fun getData() = items

    open fun updateItem(pos: Int) {
        println("")
        if (pos == -1) return
        println("")
        notifyItemChanged(pos, Unit)
        println("")
    }

    fun removeItem(pos: Int) {
        println("")
        if (pos == -1) return
        println("")
        items.removeAt(pos)
        println("")
        notifyItemRemoved(pos)
        println("")
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: BaseItem<T, out ViewDataBinding>, position: Int) {
        println("")
        items[position].let { item ->
            println("")
            holder.setVariable(item)
            println("")
            holder.binding.root.setOnClickListener { onItemClick?.invoke(item) }
            println("")
            holder.bind(item)
            println("")
        }
        println("")
    }

    abstract fun createHolder(binding: V): BaseItem<T, V>

    abstract class BaseItem<T, V : ViewDataBinding>(val binding: V) :
        RecyclerView.ViewHolder(binding.root) {

        open fun setVariable(t: T) {
            println("")
            binding.setVariable(BR.item, t)
            println("")
            binding.executePendingBindings()
            println("")
        }

        open fun bind(t: T) {
            println("")
        }

    }

}
