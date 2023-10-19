package com.example.studying.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.studying.R
import com.example.studying.core.BaseAdapter
import com.example.studying.core.BaseViewHolder
import com.example.studying.databinding.TextItemBinding

class MessagesAdapter(
    private val onClick: (MessageUi) -> Unit,
) : BaseAdapter<MessageUi, MessagesAdapter.TextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val view = TextItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        val holder = TextViewHolder(view)
        holder.apply {
            view.text.setOnClickListener {
                onClick(getItem(adapterPosition))
            }
        }

        return holder
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

    inner class TextViewHolder(private val view: TextItemBinding) : BaseViewHolder<MessageUi>(view.root) {
        override fun bind(item: MessageUi, position: Int) {
            item.map(view.text)
        }
    }
}