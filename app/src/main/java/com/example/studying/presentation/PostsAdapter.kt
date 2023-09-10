package com.example.studying.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.R
import com.example.studying.databinding.PostLayoutBinding

class PostsAdapter(
    private val onClick: (String) -> Unit
) : ListAdapter<PostUi, PostsAdapter.BaseViewHolder>(DiffUtilCallback<PostUi>()) {
    private var countCreateViewHolder = 0
    private var countBindViewHolder = 0

    inner class BaseViewHolder(private val view: PostLayoutBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(item: PostUi) {
            view.tvTitle.text = if (item is PostUi.Success) item.title else "ERROR"
            view.root.startAnimation(AnimationUtils.loadAnimation(view.root.context, R.anim.main_anim))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        Log.w("attadag", "onCreateViewHolder: ${countCreateViewHolder++}")
        val view = PostLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).apply {
            tvTitle.setOnClickListener {
                onClick(tvTitle.text.toString())
            }
        }
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        Log.w("attadag", "onBindViewHolder: ${countBindViewHolder++}")
        holder.bind(getItem(position))
    }
}