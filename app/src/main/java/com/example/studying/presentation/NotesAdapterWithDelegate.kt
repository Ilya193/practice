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

interface DelegateItem {
    fun id(item: DelegateItem): Boolean
    fun compareTo(item: DelegateItem): Boolean
    fun changePayload(item: DelegateItem): Any
}

interface AdapterDelegate {
    fun onCreateViewHolder(parent: ViewGroup, click: OnClickListener): NotesAdapter.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem)
    fun isOfViewType(item: DelegateItem): Boolean

    class Note : AdapterDelegate {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            click: OnClickListener,
        ): NotesAdapter.ViewHolder {
            val view = NoteItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            val holder = MainDelegateAdapter.NoteViewHolder(view)
            view.favorite.setOnClickListener {
                click.onClick(holder.adapterPosition)
            }
            return holder
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem) {
            (holder as MainDelegateAdapter.NoteViewHolder).bind(item as NoteUi.Note)
        }

        override fun isOfViewType(item: DelegateItem): Boolean {
            return item is NoteUi.Note
        }

    }

    class Header : AdapterDelegate {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            click: OnClickListener,
        ): NotesAdapter.ViewHolder {
            return MainDelegateAdapter.HeaderViewHolder(
                HeaderItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem) {
            (holder as MainDelegateAdapter.HeaderViewHolder).bind(item as NoteUi.Header)
        }

        override fun isOfViewType(item: DelegateItem): Boolean {
            return item is NoteUi.Header
        }

    }

}

interface OnClickListener {
    fun onClick(position: Int)
}

abstract class MainDelegateAdapter(
    private val favorite: (NoteUi.Note) -> Unit,
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

    override fun onClick(position: Int) {
        if (position != RecyclerView.NO_POSITION)
            favorite(getItem(position) as NoteUi.Note)
    }
}

class NotesAdapterWithDelegate(
    favorite: (NoteUi.Note) -> Unit,
) : MainDelegateAdapter(favorite)

class DiffDelegate : DiffUtil.ItemCallback<DelegateItem>() {
    override fun areItemsTheSame(oldItem: DelegateItem, newItem: DelegateItem): Boolean =
        oldItem::class == newItem::class && oldItem.id(newItem)

    override fun areContentsTheSame(oldItem: DelegateItem, newItem: DelegateItem): Boolean =
        oldItem.compareTo(newItem)

    override fun getChangePayload(oldItem: DelegateItem, newItem: DelegateItem): Any? =
        oldItem.changePayload(newItem)
}