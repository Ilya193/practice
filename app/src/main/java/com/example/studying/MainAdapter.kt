package com.example.studying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.databinding.ItemLayoutBinding

class MainAdapter: ListAdapter<ItemUi, MainAdapter.ViewHolder>(Diff()) {
    class ViewHolder(private val view: ItemLayoutBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(item: ItemUi) {
            view.root.text = item.num.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class Diff : DiffUtil.ItemCallback<ItemUi>() {
    override fun areItemsTheSame(oldItem: ItemUi, newItem: ItemUi): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ItemUi, newItem: ItemUi): Boolean =
        oldItem == newItem

}