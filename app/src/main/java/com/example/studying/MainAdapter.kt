package com.example.studying

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.databinding.ItemLayoutBinding

class MainAdapter(
    private val data: List<String>,
    private val change: (Int) -> Unit
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var mode: Boolean = true

    class ViewHolder(private val view: ItemLayoutBinding) : RecyclerView.ViewHolder(view.root) {

        private val dpPrev = -50f
        private val pxPrev = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpPrev,
            view.root.context.resources.displayMetrics
        )

        private val dpNext = 50f
        private val pxNext = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpNext,
            view.root.context.resources.displayMetrics
        )

        fun previous(item: String) {
            view.textView.text = item
            bind(pxPrev)
        }

        fun next(item: String) {
            view.textView.text = item
            bind(pxNext)
        }

        private fun bind(px: Float) {
            view.root.translationY = px
            view.root.animate().translationY(0f).setDuration(250).start()
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        change(holder.adapterPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mode) holder.next(data[position])
        else holder.previous(data[position])
    }
}