package com.example.studying.presentation

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.studying.databinding.ImageItemBinding

class ImagesAdapter(
    private val onClick: (ItemUi) -> Unit,
) : ListAdapter<ItemUi, ImagesAdapter.ImageViewHolder>(DiffUtilCallback()) {

    class ImageViewHolder(private val view: ImageItemBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(item: ItemUi) {
            view.image.load(item.path)
            bindLoading(item)
        }

        fun bindLoading(item: ItemUi) {
            view.status.text = if (item.uploading) "Файл загружается" else ""
            view.uploading.visibility = if (item.uploading) View.VISIBLE else View.INVISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = ImageItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val holder = ImageViewHolder(view)
        holder.apply {
            view.root.setOnClickListener {
                onClick(getItem(adapterPosition))
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: ImageViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        if (payloads.isNotEmpty()) {
            val result = payloads[0] as Boolean
            if (result) holder.bindLoading(getItem(position))
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<ItemUi>() {
    override fun areItemsTheSame(oldItem: ItemUi, newItem: ItemUi): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ItemUi, newItem: ItemUi): Boolean = oldItem == newItem

    override fun getChangePayload(oldItem: ItemUi, newItem: ItemUi): Any =
        oldItem.uploading != newItem.uploading
}