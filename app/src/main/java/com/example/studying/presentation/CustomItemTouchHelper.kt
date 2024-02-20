package com.example.studying.presentation

import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.studying.R

class CustomItemTouchHelper(
    private val onDelete: (Int) -> Unit,
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onDelete(viewHolder.adapterPosition)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
    ) {
        val itemView = viewHolder.itemView
        val deleteIcon =
            ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_delete) ?: return
        val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2
        deleteIcon.setBounds(
            itemView.right - iconMargin - deleteIcon.intrinsicWidth,
            itemView.top + iconMargin,
            itemView.right - iconMargin,
            itemView.bottom - iconMargin
        )
        deleteIcon.draw(c)
        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }
}