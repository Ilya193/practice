package com.example.studying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.databinding.ItemBinding

class InputAdapter(
    private val changeTextHidden: (Int, String) -> Unit,
    private val changeText: (Int, String) -> Unit
) : ListAdapter<User, InputAdapter.ViewHolder>(Diff()) {

    inner class ViewHolder(private val view: ItemBinding) : RecyclerView.ViewHolder(view.root) {

        init {
            view.input.addTextChangedListener(afterTextChanged = {
                changeText(adapterPosition, view.input.text.toString())
            })
        }

        fun bind(item: User) {
            view.input.setText(item.name)
        }

        fun saveText() {
            changeTextHidden(adapterPosition, view.input.text.toString())
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.saveText()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
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