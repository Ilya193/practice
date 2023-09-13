package com.example.studying

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.studying.databinding.SliderItemBinding

class SliderAdapter(
    private val context: Context,
    private val imgList: List<String>,
) : RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

    inner class ViewHolder(private val view: SliderItemBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(item: String) {
            Glide.with(context)
                .load(item)
                .centerCrop()
                .into(view.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SliderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = imgList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imgList[position])
    }
}