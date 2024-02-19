package com.example.studying.presentation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.R
import com.example.studying.databinding.HeaderItemBinding
import com.example.studying.databinding.NoteItemBinding

abstract class MainDelegateAdapter(
    private val favorite: (NoteUi.Note) -> Unit,
    private val delete: (NoteUi.Note) -> Unit,
    private val detail: (NoteUi.Note) -> Unit
) : ListAdapter<DelegateItem, RecyclerView.ViewHolder>(DiffDelegate()), OnClickListener {
    private val delegates = mutableListOf<AdapterDelegate>()

    fun addDelegate(delegate: AdapterDelegate) {
        delegates.add(delegate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegates[viewType].onCreateViewHolder(parent, this)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegates[getItemViewType(position)].onBindViewHolder(holder, getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return delegates.indexOfFirst { it.isOfViewType(currentList[position]) }
    }

    class NoteViewHolder(private val view: NoteItemBinding) :
        BaseViewHolder<NoteUi.Note>(view.root) {

        override fun bind(item: NoteUi.Note) {
            view.tvNote.text = item.text
            bindFavorite(item)
        }

        override fun bindFavorite(item: NoteUi.Note) {
            view.favorite.setImageResource(if (item.isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
        }
    }

    class HeaderViewHolder(private val view: HeaderItemBinding) :
        BaseViewHolder<NoteUi.Header>(view.root) {
        override fun bind(item: NoteUi.Header) {
            item.showText(view.root)
        }
    }

    abstract class BaseViewHolder<T : NoteUi>(view: View) : NotesAdapter.ViewHolder(view) {
        open fun bind(item: T) {}
        open fun bindFavorite(item: T) {}
    }

    override fun onFavorite(position: Int) {
        if (position != RecyclerView.NO_POSITION)
            favorite(getItem(position) as NoteUi.Note)
    }

    override fun onDelete(position: Int) {
        if (position != RecyclerView.NO_POSITION)
            delete(getItem(position) as NoteUi.Note)
    }

    override fun onDetail(position: Int) {
        if (position != RecyclerView.NO_POSITION)
            detail(getItem(position) as NoteUi.Note)
    }
}

class NotesAdapterWithDelegate(
    favorite: (NoteUi.Note) -> Unit,
    delete: (NoteUi.Note) -> Unit,
    detail: (NoteUi.Note) -> Unit,
) : MainDelegateAdapter(favorite, delete, detail)

class DiffDelegate : DiffUtil.ItemCallback<DelegateItem>() {
    override fun areItemsTheSame(oldItem: DelegateItem, newItem: DelegateItem): Boolean =
        oldItem::class == newItem::class && oldItem.id(newItem)

    override fun areContentsTheSame(oldItem: DelegateItem, newItem: DelegateItem): Boolean =
        oldItem.compareTo(newItem)

    override fun getChangePayload(oldItem: DelegateItem, newItem: DelegateItem): Any? =
        oldItem.changePayload(newItem)
}