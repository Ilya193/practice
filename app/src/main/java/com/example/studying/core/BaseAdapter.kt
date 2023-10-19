package com.example.studying.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.presentation.CustomTextView
import com.example.studying.presentation.Listeners
import com.example.studying.presentation.MessageUi
import com.example.studying.R
import com.example.studying.databinding.TextItemBinding

abstract class BaseAdapter<T : Comparing<T>, E : BaseViewHolder<T>> :
    ListAdapter<T, E>(DiffUtilCallback<T>()) {
    override fun onBindViewHolder(holder: E, position: Int) {
        holder.bind(getItem(position), position)
    }
}

abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(item: T, position: Int)
}