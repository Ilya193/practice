package com.example.studying

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.databinding.HeaderItemBinding
import com.example.studying.databinding.NoteItemBinding

class NotesAdapter(
    private val favorite: (NoteUi.Note) -> Unit
) : ListAdapter<NoteUi, NotesAdapter.BaseViewHolder>(Diff()) {

    abstract class BaseViewHolder(view: View): RecyclerView.ViewHolder(view) {
        open fun bind(item: NoteUi) {}
        open fun bindFavorite(item: NoteUi) {}
    }

    inner class NoteViewHolder(private val view: NoteItemBinding): BaseViewHolder(view.root) {
        init {
            view.favorite.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    favorite(getItem(adapterPosition) as NoteUi.Note)
            }
        }

        override fun bind(item: NoteUi) {
            item.showText(view.tvNote)
            bindFavorite(item)
        }

        override fun bindFavorite(item: NoteUi) {
            item.showIcon(view.favorite)
        }
    }

    inner class HeaderViewHolder(private val view: HeaderItemBinding): BaseViewHolder(view.root) {
        override fun bind(item: NoteUi) {
            item.showText(view.root)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is NoteUi.Note -> VIEW_TYPE_NOTE
            is NoteUi.Header -> VIEW_TYPE_HEADER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_NOTE -> NoteViewHolder(NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            VIEW_TYPE_HEADER -> HeaderViewHolder(HeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> TODO()
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads)
        else holder.bindFavorite(getItem(position))
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