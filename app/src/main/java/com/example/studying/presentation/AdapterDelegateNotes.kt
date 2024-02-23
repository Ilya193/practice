package com.example.studying.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.R
import com.example.studying.databinding.HeaderItemBinding
import com.example.studying.databinding.NoteItemBinding

interface AdapterDelegateNotes {
    fun onCreateViewHolder(
        parent: ViewGroup,
        clickListener: OnClickListenerNotes,
    ): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem)
    fun isOfViewType(item: DelegateItem): Boolean

    class Note : AdapterDelegateNotes {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            clickListener: OnClickListenerNotes,
        ): RecyclerView.ViewHolder {
            val view = NoteItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            val holder = MainDelegateAdapter.NoteViewHolder(view)
            view.favorite.setOnClickListener {
                val anim = AnimationUtils.loadAnimation(it.context, R.anim.like_animation)
                it.startAnimation(anim)
                clickListener.onFavorite(holder.adapterPosition)
            }
            view.root.setOnLongClickListener {
                clickListener.onDelete(holder.adapterPosition)
                true
            }
            view.root.setOnClickListener {
                clickListener.onDetail(holder.adapterPosition)
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

    class Header : AdapterDelegateNotes {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            clickListener: OnClickListenerNotes,
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