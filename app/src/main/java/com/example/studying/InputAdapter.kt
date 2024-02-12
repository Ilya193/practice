package com.example.studying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.databinding.ItemBinding

class InputAdapter(
    private val add: () -> Unit,
    private val delete: () -> Unit
) : ListAdapter<User, InputAdapter.ViewHolder>(Diff()) {

    override fun submitList(list: MutableList<User>?) {
        list?.let {
            end = list.size - 5
            start += 10
        }
        super.submitList(list)
    }

    private var start = 20
    private var end = currentList.size - 5

    inner class ViewHolder(private val view: ItemBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(item: User) {
            view.input.setText(item.name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == end) add()
        if (position == start) delete()
        holder.bind(getItem(position))
    }
}

data class User(
    val id: Long = System.currentTimeMillis(),
    val name: String = ""
)

class Diff : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem

}