package com.example.studying

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.databinding.ItemBinding

class TestAdapter(
    private val upload: (Int) -> Unit
): ListAdapter<User, TestAdapter.ViewHolder>(Diff()) {

    inner class ViewHolder(private val view: ItemBinding) : RecyclerView.ViewHolder(view.root) {

        init {
            view.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    upload(adapterPosition)
            }
        }

        fun bind(item: User) {
            bindProgress(item)
        }

        fun bindProgress(item: User) {
            view.uploading.visibility = if (item.uploading) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        println("s149 payloads.size = ${payloads.size}")
        if (payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads)
        else holder.bindProgress(getItem(position))
    }
}

data class User(
    val id: Long = System.currentTimeMillis(),
    val name: String = "",
    val uploading: Boolean = false
)

class Diff : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: User, newItem: User): Any? =
        oldItem.uploading != newItem.uploading

}