package com.example.studying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.databinding.NoteItemBinding

class NotesAdapter(
    private val favorite: (NoteUi) -> Unit
) : ListAdapter<NoteUi, NotesAdapter.ViewHolder>(Diff()) {

    inner class ViewHolder(private val view: NoteItemBinding): RecyclerView.ViewHolder(view.root) {

        init {
            view.favorite.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    favorite(getItem(adapterPosition))
            }
        }

        fun bind(item: NoteUi) {
            view.tvNote.text = item.text
            bindFavorite(item)
        }

        fun bindFavorite(item: NoteUi) {
            view.favorite.setImageResource(if (item.isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads)
        else holder.bindFavorite(getItem(position))
    }
}

class Diff : DiffUtil.ItemCallback<NoteUi>() {
    override fun areItemsTheSame(oldItem: NoteUi, newItem: NoteUi): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NoteUi, newItem: NoteUi): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: NoteUi, newItem: NoteUi): Any? =
        oldItem.isFavorite != newItem.isFavorite

}