package com.example.studying

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.studying.databinding.ItemBinding

class PostAdapter : BaseAdapter<PostUi, BaseViewHolder<PostUi>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<PostUi> {
        return TextViewHolder(ItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    class TextViewHolder(view: ItemBinding) : BaseViewHolder<PostUi>(view.root) {
        private val textView: CustomTextView = view.root.findViewById(R.id.textView)
        override fun bind(item: PostUi, position: Int) {
            item.map(textView)
        }
    }
}