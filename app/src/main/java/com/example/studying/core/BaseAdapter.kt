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

class MessagesAdapter(
    private val listeners: Listeners,
) : BaseAdapter<MessageUi, MessagesAdapter.TextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        return TextViewHolder(
            TextItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: TextViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else {
            val bundle = payloads[0] as Bundle
            if (bundle.size() != 0) {
                val isChange = bundle.getBoolean("newItem")
                if (isChange) holder.bind(getItem(position), position)
            }
        }
    }

    inner class TextViewHolder(view: TextItemBinding) : BaseViewHolder<MessageUi>(view.root) {
        private val text: CustomTextView = view.root.findViewById(R.id.text)

        override fun bind(item: MessageUi, position: Int) {
            item.map(text)
            text.setOnClickListener {
                listeners.onClick(item)
            }
        }
    }
}