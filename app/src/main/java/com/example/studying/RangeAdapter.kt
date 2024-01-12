package com.example.studying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.databinding.ItemBinding

class RangeAdapter(
    private val onClick: (Int, ItemUi) -> Unit
) : ListAdapter<ItemUi, RangeAdapter.ViewHolder>(DiffUtil()) {
    class ViewHolder(private val view: ItemBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(item: ItemUi) {
            view.tvText.text = item.text
            bindChecked(item)
        }

        fun bindChecked(item: ItemUi) {
            view.checkbox.isChecked = item.checked
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view).apply {
            view.checkbox.setOnClickListener {
                onClick(adapterPosition, getItem(adapterPosition))
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads)
        else {
            val result = payloads[0] as Boolean
            if (result) holder.bindChecked(getItem(position))
        }
    }
}

class DiffUtil: DiffUtil.ItemCallback<ItemUi>() {
    override fun areItemsTheSame(oldItem: ItemUi, newItem: ItemUi): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: ItemUi, newItem: ItemUi): Boolean =
        oldItem == newItem


    override fun getChangePayload(oldItem: ItemUi, newItem: ItemUi): Any? =
        oldItem.checked != newItem.checked

}

data class ItemUi(
    val id: Int,
    val text: String,
    val checked: Boolean = false
)