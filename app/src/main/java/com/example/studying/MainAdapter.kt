package com.example.studying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.databinding.ItemLayoutBinding

class MainAdapter: ListAdapter<ItemUi, MainAdapter.ViewHolder>(Diff()) {
    class ViewHolder(private val view: ItemLayoutBinding) : RecyclerView.ViewHolder(view.root) {

        init {
            view.root.setOnClickListener {
                view.data.animate().scaleY(1.2f).scaleX(1.2f)
                    .rotation(1f).withEndAction {
                        view.data.animate().scaleY(1.2f).scaleX(1.2f).rotation(-1f)
                            .setDuration(50).withEndAction {
                                view.data.animate().scaleY(1f).scaleX(1f).rotation(0f)
                                    .setDuration(50).start()
                            }.start()
                    }.setDuration(50).start()
            }
        }

        fun bind(item: ItemUi) {
            view.data.text = item.num.toString()
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