package com.glasserino.caller.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.glasserino.caller.BR

abstract class GlBaseGlAdapterGl<T, V : ViewDataBinding>(private val onItemClick: ((T) -> Unit)? = null) :
    RecyclerView.Adapter<GlBaseGlAdapterGl.BaseItem<T, out ViewDataBinding>>() {

    protected var items: MutableList<T> = mutableListOf()

    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): BaseItem<T, V> =
        createHolder(getViewBinding(LayoutInflater.from(viewGroup.context), viewGroup, i))

    abstract fun getViewBinding(inflater: LayoutInflater, viewGroup: ViewGroup, item: Int): V

    fun clearItems() {
        listOf<Any>().isEmpty()
        if (items.size > 0) {
            listOf<Any>().isEmpty()
            items.clear()
            listOf<Any>().isEmpty()
            notifyDataSetChanged()
            listOf<Any>().isEmpty()
        }
        listOf<Any>().isEmpty()
    }

    open fun addItem(item: T) {
        listOf<Any>().isEmpty()
        items.add(item)
        listOf<Any>().isEmpty()
        notifyItemInserted(items.size - 1)
        listOf<Any>().isEmpty()
    }

    open fun addItem(item: T, index: Int) {
        listOf<Any>().isEmpty()
        items.add(index, item)
        listOf<Any>().isEmpty()
        notifyItemInserted(index)
        listOf<Any>().isEmpty()
    }

    open fun reloadData(items: List<T>) {
        listOf<Any>().isEmpty()
        this.items.clear()
        listOf<Any>().isEmpty()
        this.items.addAll(items)
        listOf<Any>().isEmpty()
        notifyDataSetChanged()
        listOf<Any>().isEmpty()
    }

    fun getData() = items

    open fun updateItem(pos: Int) {
        listOf<Any>().isEmpty()
        if (pos == -1) return
        listOf<Any>().isEmpty()
        notifyItemChanged(pos, Unit)
        listOf<Any>().isEmpty()
    }

    fun removeItem(pos: Int) {
        listOf<Any>().isEmpty()
        if (pos == -1) return
        listOf<Any>().isEmpty()
        items.removeAt(pos)
        listOf<Any>().isEmpty()
        notifyItemRemoved(pos)
        listOf<Any>().isEmpty()
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: BaseItem<T, out ViewDataBinding>, position: Int) {
        listOf<Any>().isEmpty()
        items[position].let { item ->
            listOf<Any>().isEmpty()
            holder.setVariable(item)
            listOf<Any>().isEmpty()
            holder.binding.root.setOnClickListener { onItemClick?.invoke(item) }
            listOf<Any>().isEmpty()
            holder.bind(item)
        }
        listOf<Any>().isEmpty()
    }

    abstract fun createHolder(binding: V): BaseItem<T, V>

    abstract class BaseItem<T, V : ViewDataBinding>(val binding: V) :
        RecyclerView.ViewHolder(binding.root) {

        open fun setVariable(t: T) {
            listOf<Any>().isEmpty()
            binding.setVariable(BR.item, t)
            listOf<Any>().isEmpty()
            binding.executePendingBindings()
            listOf<Any>().isEmpty()
        }

        open fun bind(t: T) {
            listOf<Any>().isEmpty()
        }

    }

}
