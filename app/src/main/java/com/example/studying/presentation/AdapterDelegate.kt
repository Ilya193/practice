package com.example.studying.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.databinding.HeaderItemBinding
import com.example.studying.databinding.NoteItemBinding
import com.example.studying.databinding.TaskItemBinding

interface AdapterDelegate {
    fun onCreateViewHolder(parent: ViewGroup, clickListener: OnClickListener): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem)
    fun isOfViewType(item: DelegateItem): Boolean

    class Note : AdapterDelegate {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            clickListener: OnClickListener,
        ): RecyclerView.ViewHolder {
            val view = NoteItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            val holder = MainDelegateAdapter.NoteViewHolder(view)
            view.favorite.setOnClickListener {
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

    class Header : AdapterDelegate {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            clickListener: OnClickListener,
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

    class Task : AdapterDelegate {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            clickListener: OnClickListener,
        ): RecyclerView.ViewHolder {
            return TasksAdapter.TaskViewHolder(
                TaskItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem) {
            (holder as TasksAdapter.TaskViewHolder).bind(item as TaskUi.Task)
        }

        override fun isOfViewType(item: DelegateItem): Boolean {
            return item is TaskUi.Task
        }

    }

}