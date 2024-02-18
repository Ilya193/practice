package com.example.studying.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.R
import com.example.studying.databinding.HeaderItemBinding
import com.example.studying.databinding.NoteItemBinding

class NotesAdapter(
    private val favorite: (NoteUi.Note) -> Unit
) : ListAdapter<NoteUi, NotesAdapter.ViewHolder>(Diff()) {

    abstract class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    abstract class BaseViewHolder<T: NoteUi>(view: View): ViewHolder(view) {
        open fun bind(item: T) {}
        open fun bindFavorite(item: T) {}
    }

    inner class NoteViewHolder(private val view: NoteItemBinding): BaseViewHolder<NoteUi.Note>(view.root) {

        init {
            view.favorite.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    favorite(getItem(adapterPosition) as NoteUi.Note)
            }
        }

        override fun bind(item: NoteUi.Note) {
            view.tvNote.text = item.text
            bindFavorite(item)
        }

        override fun bindFavorite(item: NoteUi.Note) {
            view.favorite.setImageResource(if (item.isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
        }
    }

    inner class HeaderViewHolder(private val view: HeaderItemBinding): BaseViewHolder<NoteUi.Header>(view.root) {
        override fun bind(item: NoteUi.Header) {
            item.showText(view.root)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is NoteUi.Note -> VIEW_TYPE_NOTE
            is NoteUi.Header -> VIEW_TYPE_HEADER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_NOTE -> NoteViewHolder(NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> HeaderViewHolder(HeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_NOTE -> (holder as NoteViewHolder).bind(getItem(position) as NoteUi.Note)
            VIEW_TYPE_HEADER -> (holder as HeaderViewHolder).bind(getItem(position) as NoteUi.Header)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads)
        else {
            when (getItemViewType(position)) {
                VIEW_TYPE_NOTE -> (holder as NoteViewHolder).bindFavorite(getItem(position) as NoteUi.Note)
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 1
        private const val VIEW_TYPE_NOTE = 2
    }
}

class Diff : DiffUtil.ItemCallback<NoteUi>() {
    override fun areItemsTheSame(oldItem: NoteUi, newItem: NoteUi): Boolean =
        oldItem.same(newItem)

    override fun areContentsTheSame(oldItem: NoteUi, newItem: NoteUi): Boolean =
        oldItem.sameContent(newItem)

    override fun getChangePayload(oldItem: NoteUi, newItem: NoteUi): Any? =
        oldItem.changePayload(newItem)

}