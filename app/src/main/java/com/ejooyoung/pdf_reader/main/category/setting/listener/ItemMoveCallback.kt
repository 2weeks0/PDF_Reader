package com.ejooyoung.pdf_reader.main.category.setting.listener

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ejooyoung.pdf_reader.base.utils.DevLogger

class ItemMoveCallback(
    private val onItemMoveListener: OnItemMoveListener
): ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        DevLogger.i("return: ${makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0)}")
        return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        DevLogger.i("beforePosition: ${viewHolder.adapterPosition}, toPosition: ${target.adapterPosition}")
        onItemMoveListener.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        DevLogger.i()
    }

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }
}