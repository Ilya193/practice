package com.example.studying.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.databinding.TaskItemBinding

interface AdapterDelegateTasks {
    fun onCreateViewHolder(parent: ViewGroup, clickListener: OnClickListenerTasks): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem)
    fun isOfViewType(item: DelegateItem): Boolean

    class Task : AdapterDelegateTasks {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            clickListener: OnClickListenerTasks,
        ): RecyclerView.ViewHolder {
            val view = TaskItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            val holder = TasksAdapter.TaskViewHolder(view)
            view.root.setOnLongClickListener {
                clickListener.onDelete(holder.adapterPosition)
                true
            }

            return holder
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem) {
            (holder as TasksAdapter.TaskViewHolder).bind(item as TaskUi.Task)
        }

        override fun isOfViewType(item: DelegateItem): Boolean {
            return item is TaskUi.Task
        }

    }
}