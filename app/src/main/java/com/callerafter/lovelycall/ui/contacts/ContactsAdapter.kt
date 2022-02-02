package com.callerafter.lovelycall.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.callerafter.lovelycall.R
import com.callerafter.lovelycall.base.BaseAdapter
import com.callerafter.lovelycall.databinding.ItemContactGridBinding
import com.callerafter.lovelycall.databinding.ItemContactLinearBinding
import com.callerafter.lovelycall.model.contact.UserContactViewModel
import de.hdodenhof.circleimageview.CircleImageView

class ContactsAdapter(
    onItemClick: (UserContactViewModel) -> Unit
) : BaseAdapter<UserContactViewModel, ViewDataBinding>(onItemClick) {

    var viewType: ViewType = ViewType.GRID
        set(value) {
            if (value != field) {
                val items = mutableListOf<UserContactViewModel>().apply { addAll(getData()) }
                clearItems()
                field = value
                reloadData(items)
            }
        }

    override fun getViewBinding(inflater: LayoutInflater, viewGroup: ViewGroup, item: Int) = when (viewType) {
        ViewType.GRID -> ItemContactGridBinding.inflate(inflater, viewGroup, false)
        ViewType.LINEAR -> ItemContactLinearBinding.inflate(inflater, viewGroup, false)
    }

    override fun getItemViewType(position: Int) = viewType.id

    override fun createHolder(binding: ViewDataBinding) =
        object : BaseItem<UserContactViewModel, ViewDataBinding>(binding) {
            override fun bind(t: UserContactViewModel) {
                val imageView = binding.root.findViewById<CircleImageView>(R.id.thumbnail)
                Glide
                    .with(imageView)
                    .let {
                        if (t.contact.photoThumbnailUri !== null) it.load(t.contact.photoThumbnailUri)
                        else it.load(R.mipmap.ic_no_logo)
                    }
                    .into(imageView)
            }
        }

    enum class ViewType(val id: Int) {
        GRID(0), LINEAR(1)
    }

}