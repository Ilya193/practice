package com.example.studying.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.R
import com.example.studying.databinding.PostLayoutBinding

class PostsAdapter(
    private val onDelete: (PostUi.Success) -> Unit,
    private val onFavorite: (PostUi.Success) -> Unit,
) : ListAdapter<PostUi.Success, PostsAdapter.BaseViewHolder>(DiffUtilCallback<PostUi.Success>()) {
    private var countCreateViewHolder = 0
    private var countBindViewHolder = 0

    inner class BaseViewHolder(private val view: PostLayoutBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(item: PostUi.Success) {
            view.tvTitle.text = item.title
            view.root.startAnimation(
                AnimationUtils.loadAnimation(
                    view.root.context,
                    R.anim.main_anim
                )
            )
            bindFavorite(item)
        }

        fun bindFavorite(item: PostUi.Success) {
            if (item.isFavorite)
                view.icFavorite.setBackgroundResource(R.drawable.baseline_favorite_24)
            else
                view.icFavorite.setBackgroundResource(R.drawable.baseline_favorite_border_24)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = PostLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val holder = BaseViewHolder(view)
        holder.apply {
            view.root.setOnClickListener {
                onDelete(getItem(adapterPosition))
            }
            view.icFavorite.setOnClickListener {
                onFavorite(getItem(adapterPosition))
            }
        }
        return holder
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        if (payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads)
        else if (payloads[0] == true) holder.bindFavorite(getItem(position))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}