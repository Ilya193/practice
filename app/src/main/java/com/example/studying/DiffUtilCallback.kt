package com.example.studying

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback<T : Comparing<T>> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.same(newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.sameContent(newItem)
    }
}