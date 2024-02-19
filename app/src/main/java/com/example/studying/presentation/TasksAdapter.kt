package com.example.studying.presentation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.databinding.TaskItemBinding

class TasksAdapter(
    private val delete: (TaskUi.Task) -> Unit
) : ListAdapter<DelegateItem, RecyclerView.ViewHolder>(DiffDelegate()), OnClickListenerTasks {

    private val delegates = mutableListOf<AdapterDelegateTasks>()

    fun addDelegate(delegate: AdapterDelegateTasks) {
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

    abstract class BaseViewHolder<T: TaskUi>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: T)
    }

    class TaskViewHolder(private val view: TaskItemBinding) : BaseViewHolder<TaskUi.Task>(view.root) {

        override fun bind(item: TaskUi.Task) {
            view.tvNote.text = item.text
        }
    }

    override fun onDelete(position: Int) {
        delete(getItem(position) as TaskUi.Task)
    }
}